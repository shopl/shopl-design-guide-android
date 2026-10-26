---
name: sdg-android-foundation
description: 배포된 SDG를 사용하는 소비 앱에서 토큰·아이콘을 조회하고 적용한다. SDG 저장소 자체의 토큰·컴포넌트·샘플 개발에는 저장소 Builder 규칙을 따른다.
---

# Android SDG 토큰과 아이콘

## 적용 범위

이 Skill은 **배포된 SDG를 사용하는 소비 앱 개발**에 적용한다. SDG 저장소 자체의 컴포넌트·샘플 개발에는 소비 앱 절차를 적용하지 않고, 대상 저장소의 `AGENTS.md`와 `.agents/skills/sdg-builder-rule/SKILL.md`를 우선 따른다.

SDG 자체 개발에서는 실제 체크아웃의 선언·호출부가 기준이다. MCP는 최신 배포 참조이므로 검색에 없거나 시그니처가 다르다는 이유만으로 미배포 API를 없다고 판단하거나 삭제·되돌리지 않는다. 배포 참조가 필요하면 `sdg_resolve_context(workContext: "sdg_source")`로 조회한다. Builder가 Git·빌드·PR을 관리하는 흐름에서는 내부 AI가 허용된 Kotlin 소스만 수정한다. 이 Skill을 근거로 의존성·배포 버전을 바꾸거나 Gradle·Git·PR 작업, MCP 생성을 실행하지 않는다. 문자열·리소스 정책도 대상 저장소 지침을 따른다.

## 소비 앱 절차

- `sdg_search_assets`의 kind(color, typography, spacing, radius, icon)와 검색어로 실제 선언을 조회한다. assetId로 정확한 항목을 다시 조회할 수 있다.
- 응답의 Kotlin reference와 import를 사용한다. 이름이 비슷하다는 이유로 토큰·drawable·스타일을 만들어 쓰지 않는다. 값은 소스 표현식일 수 있으므로 계산된 색상·크기로 단정하지 않는다.
- component·template·기존 kit에서 이미 제공하는 스타일을 먼저 사용한다. 디자인에 맞는 토큰이 없으면 임의로 가장 가까운 값으로 바꾸지 말고 차이를 보고한다.
- 아이콘의 drawable 참조와 필요한 모듈을 확인한다. 이미지가 원래 색을 보존해야 하는지, tint가 필요한지는 기존 사용 예와 디자인을 기준으로 판단한다.
- 소비 앱이 이전 SDG 버전이면 저장소 지침과 허용된 변경 범위 안에서 최신 의존성과 실제 해석을 먼저 맞춘다. 컴파일·Gradle 확인은 저장소가 허용한 실행 주체·절차를 따른다. 앱 리소스 변경과 문자열 연결에는 저장소 정책을 적용한다.
