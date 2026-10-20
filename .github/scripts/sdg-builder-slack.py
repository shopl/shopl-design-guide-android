"""SDG Builder의 시작·완료 알림을 #sdg-builder-build에 전송한다."""

import json
import os
import re
import sys
from http.client import HTTPException
from urllib.error import HTTPError, URLError
from urllib.request import Request, urlopen

REPOSITORY = 'shopl/shopl-design-guide-android'
TEAM = 'T016H73FHDH'
CHANNEL = 'C0CAC49CLH3'  # #sdg-builder-build


def request_json(url, payload, token):
    request = Request(url, data=json.dumps(payload).encode(), headers={
        'Authorization': f'Bearer {token}',
        'Content-Type': 'application/json; charset=utf-8',
    })
    try:
        with urlopen(request, timeout=20) as response:
            result = json.load(response)
    except HTTPError as error:
        raise RuntimeError(f'Slack API HTTP {error.code}') from None
    except (URLError, OSError, HTTPException, ValueError):
        # 전송 뒤 응답만 유실됐을 수 있으므로 자동 재전송하지 않는다.
        raise RuntimeError('Slack API connection or response failed; delivery is uncertain') from None
    if not result.get('ok'):
        code = str(result.get('error', 'unknown_error'))
        raise RuntimeError('Slack API: ' + (code if re.fullmatch(r'[a-z_]+', code) else 'unknown_error'))
    return result


def make_payload():
    if os.environ.get('GITHUB_REPOSITORY') != REPOSITORY or os.environ.get('GITHUB_REF') != 'refs/heads/main':
        raise RuntimeError('Only the trusted company main workflow is allowed')
    sha = os.environ['SOURCE_SHA']
    run_id, attempt = os.environ['GITHUB_RUN_ID'], os.environ['GITHUB_RUN_ATTEMPT']
    if not re.fullmatch(r'[0-9a-f]{40}', sha) or not run_id.isdigit() or not attempt.isdigit():
        raise RuntimeError('Invalid workflow identity')
    phase = os.environ['SDG_NOTIFICATION_PHASE']
    if phase not in ('started', 'finished'):
        raise RuntimeError('Invalid notification phase')
    result = os.environ.get('SDG_BUILD_RESULT', '')
    if phase == 'finished' and result not in ('success', 'failure', 'cancelled', 'skipped'):
        raise RuntimeError('Invalid build result')
    run_url = f'https://github.com/{REPOSITORY}/actions/runs/{run_id}'
    heading = 'SDG Android 빌드 시작' if phase == 'started' else {
        'success': 'SDG Android 빌드 완료', 'failure': 'SDG Android 빌드 실패',
        'cancelled': 'SDG Android 빌드 취소', 'skipped': 'SDG Android 빌드 미실행',
    }[result]
    details = '\n'.join([
        '디자인: ' + os.environ.get('DESIGN_NAME', '')[:200],
        '브랜치: ' + os.environ.get('SOURCE_BRANCH', '')[:300],
        f'커밋: {sha[:12]}', f'실행: {run_id} · {attempt}회차',
    ])
    links = []
    explanation = '러너 배정 후 앱을 빌드합니다. 완료 결과는 이 스레드에 안내합니다.'
    if phase == 'finished':
        explanation = '실행 로그에서 결과를 확인하세요.'
        apk_url = os.environ.get('SDG_APK_URL', '')
        if result == 'success' and apk_url:
            filename = f'sdg-builder-{run_id}-{attempt}-{sha[:12]}.apk'
            allowed = [f'http://192.168.31.{host}:8080/sdg-builder/{filename}' for host in (112, 113)]
            if apk_url not in allowed:
                raise RuntimeError('Unexpected APK share URL')
            links.append(('APK 다운로드', apk_url))
            explanation = 'SHOPL_DEV 네트워크에 연결한 Android 기기에서 APK를 내려받아 설치하세요.'
        elif result == 'success':
            explanation = 'APK 빌드는 성공했지만 공유 폴더 전달은 완료하지 못했습니다. 공유 단계 로그를 확인하세요.'
    links.append(('Actions 실행 보기', run_url))
    blocks = [
        {'type': 'header', 'text': {'type': 'plain_text', 'text': heading}},
        {'type': 'section', 'text': {'type': 'plain_text', 'text': details + '\n' + explanation}},
        {'type': 'actions', 'elements': [
            {'type': 'button', 'action_id': f'sdg_builder_link_{index}',
             'text': {'type': 'plain_text', 'text': label}, 'url': url}
            for index, (label, url) in enumerate(links)
        ]},
    ]
    payload = {'channel': CHANNEL, 'text': heading + '\n' + details + '\n' + explanation,
               'blocks': blocks, 'mrkdwn': False, 'parse': 'none', 'unfurl_links': False, 'unfurl_media': False}
    thread = os.environ.get('SDG_SLACK_THREAD_TS', '')
    if phase == 'finished' and thread:
        if not re.fullmatch(r'[0-9]+\.[0-9]+', thread):
            raise RuntimeError('Invalid start-message timestamp')
        payload.update(thread_ts=thread, reply_broadcast=False)
    return payload


def main():
    payload = make_payload()
    if sys.argv[1:] == ['--preview']:
        print(json.dumps(payload, ensure_ascii=False, indent=2))
        return
    if sys.argv[1:]:
        raise RuntimeError('Only --preview is supported')
    token = os.environ.get('SDG_BUILDER_SLACK_TOKEN', '')
    if not token.startswith('xoxb-'):
        raise RuntimeError('Configure SDG_BUILDER_SLACK_TOKEN with chat:write for #sdg-builder-build')
    auth = request_json('https://slack.com/api/auth.test', {}, token)
    if auth.get('team_id') != TEAM:
        raise RuntimeError('Unexpected Slack workspace')
    result = request_json('https://slack.com/api/chat.postMessage', payload, token)
    if result.get('channel') != CHANNEL or not re.fullmatch(r'[0-9]+\.[0-9]+', str(result.get('ts', ''))):
        raise RuntimeError('Slack did not confirm the requested channel/message')
    if os.environ['SDG_NOTIFICATION_PHASE'] == 'started':
        with open(os.environ['GITHUB_OUTPUT'], 'a') as output:
            output.write(f'thread_ts={result["ts"]}\n')
    with open(os.environ['GITHUB_STEP_SUMMARY'], 'a') as summary:
        summary.write('## Slack delivered\nChannel: #sdg-builder-build\n')
    print('Slack confirmed delivery to #sdg-builder-build.')


if __name__ == '__main__':
    try:
        main()
    except (RuntimeError, KeyError) as error:
        print(f'::error::{error}', file=sys.stderr)
        sys.exit(1)
