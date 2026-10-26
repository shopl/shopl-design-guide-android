---
name: sdg-android-setup
description: 배포된 SDG를 사용하는 소비 앱의 의존성 연결·업데이트·import 문제에 적용한다. SDG 저장소 자체의 컴포넌트·샘플 개발에는 저장소 Builder 규칙을 따른다.
---

# Android SDG 설정

## 적용 범위

이 Skill은 **배포된 SDG를 사용하는 소비 앱 개발**에 적용한다. SDG 저장소 자체의 컴포넌트·샘플 개발에는 소비 앱 절차를 적용하지 않고, 대상 저장소의 `AGENTS.md`와 `.agents/skills/sdg-builder-rule/SKILL.md`를 우선 따른다.

SDG 자체 개발에서는 실제 체크아웃의 선언·호출부가 기준이다. MCP는 최신 배포 참조이므로 검색에 없거나 시그니처가 다르다는 이유만으로 미배포 API를 없다고 판단하거나 삭제·되돌리지 않는다. 배포 참조가 필요하면 `sdg_resolve_context(workContext: "sdg_source")`로 조회한다. Builder가 Git·빌드·PR을 관리하는 흐름에서는 내부 AI가 허용된 Kotlin 소스만 수정한다. 이 Skill을 근거로 의존성·배포 버전을 바꾸거나 Gradle·Git·PR 작업, MCP 생성을 실행하지 않는다. 문자열·리소스 정책도 대상 저장소 지침을 따른다.

## 소비 앱 절차

1. 저장소 지침, 중앙 버전 선언, 대상 모듈과 variant를 확인한다. `sdg_resolve_context`(기본 `consumer_app`)로 MCP의 최신 배포 카탈로그와 설정을 조회한다. 도구가 없으면 연결 문제를 알리고 저장소의 실제 선언을 확인한다. 조회하지 않은 API를 추측하지 않는다.
2. MCP는 도구 호출 전에 SDG 정보를 자동 갱신한다. `update.state`가 current이면 공개된 최신 검증 정보와 일치한다. offline 또는 remote_behind이면 사용 중인 버전과 실패 사유를 알리고 최신이라고 단정하지 않는다. client_update_required일 때만 MCP 프로그램 갱신이 필요하다. 일반 SDG 정보 갱신을 위해 재설치나 자동 업데이트 설정을 요구하지 않는다. Maven Central의 SDG-Android·Common·Resource 배포가 응답보다 앞서면 게시 검증이 아직 끝나지 않았을 수 있으므로 차이를 보고하고 새 API 적용을 보류한다.
3. 앱 버전이 낮으면 대상 저장소 지침과 허용된 변경 범위 안에서 기존 중앙 선언을 최신 배포로 맞춘다. 대상 variant의 `dependencyInsight` 결과와 소비 모듈 컴파일을 저장소가 허용한 실행 주체·절차로 확인한다. 이 Skill이 에이전트의 직접 Gradle 실행 권한을 부여하지 않는다. 버전 선언만 읽었으면 `declared`, 실제 Gradle 결과가 있을 때만 `resolved` 근거를 전달한다. `dirty`는 수정 중인 SDG 소스 근거에만 사용한다.
4. SDG 버전, sourceSha, catalogId, 의존성 해석과 컴파일 결과를 보고한다. 선언 일치와 컴파일 성공을 기기 런타임 검증으로 표현하지 않는다.

MCP는 읽기 전용이다. 앱 의존성 수정은 작업 에이전트가 수행한다. Maven의 배포 버전과 MCP 서버 버전은 서로 다른 값이다. 문자열 리소스와 변경 레이어 리뷰는 대상 저장소 지침을 따른다.
