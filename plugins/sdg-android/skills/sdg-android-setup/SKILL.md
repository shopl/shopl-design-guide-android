---
name: sdg-android-setup
description: Android 저장소에서 SDG 의존성을 처음 연결하거나 업데이트할 때, SDG API와 앱 버전이 다르거나 import가 해결되지 않을 때 사용한다.
---

# Android SDG 설정

1. 저장소 지침, 중앙 버전 선언, 대상 모듈과 variant를 확인한다. `sdg_resolve_context`로 설치된 MCP의 최신 배포 카탈로그와 설정을 조회한다. 도구가 없으면 연결 문제를 알리고 저장소의 실제 선언을 확인한다. 조회하지 않은 API를 추측하지 않는다.
2. MCP는 도구 호출 전에 SDG 정보를 자동 갱신한다. `update.state`가 current이면 공개된 최신 검증 정보와 일치한다. offline 또는 remote_behind이면 사용 중인 버전과 실패 사유를 알리고 최신이라고 단정하지 않는다. client_update_required일 때만 MCP 프로그램 갱신이 필요하다. 일반 SDG 정보 갱신을 위해 재설치나 자동 업데이트 설정을 요구하지 않는다. Maven Central의 SDG-Android·Common·Resource 배포가 응답보다 앞서면 게시 검증이 아직 끝나지 않았을 수 있으므로 차이를 보고하고 새 API 적용을 보류한다.
3. 앱 버전이 낮으면 기존 중앙 선언을 최신 배포로 맞춘다. 대상 variant의 `dependencyInsight`로 세 모듈의 실제 해석을 확인하고 소비 모듈을 컴파일한다. 버전 선언만 읽었으면 `declared`, 실제 Gradle 결과가 있을 때만 `resolved` 근거를 전달한다. `dirty`는 수정 중인 SDG 소스 근거에만 사용한다.
4. SDG 버전, sourceSha, catalogId, 의존성 해석과 컴파일 결과를 보고한다. 선언 일치와 컴파일 성공을 기기 런타임 검증으로 표현하지 않는다.

MCP는 읽기 전용이다. 앱 의존성 수정은 작업 에이전트가 수행한다. Maven의 배포 버전과 MCP 서버 버전은 서로 다른 값이다. 문자열 리소스와 변경 레이어 리뷰는 대상 저장소 지침을 따른다.
