---
name: sdg-android-components
description: 배포된 SDG를 사용하는 소비 앱의 Compose 화면 개발에 적용한다. component·template·앱 kit과 정확한 API를 확인한다. SDG 저장소 자체의 컴포넌트·샘플 개발에는 저장소 Builder 규칙을 따른다.
---

# Android SDG 화면 구현

## 적용 범위

이 Skill은 **배포된 SDG를 사용하는 소비 앱 개발**에 적용한다. SDG 저장소 자체의 컴포넌트·샘플 개발에는 소비 앱 절차를 적용하지 않고, 대상 저장소의 `AGENTS.md`와 `.agents/skills/sdg-builder-rule/SKILL.md`를 우선 따른다.

SDG 자체 개발에서는 실제 체크아웃의 선언·호출부가 기준이다. MCP는 최신 배포 참조이므로 검색에 없거나 시그니처가 다르다는 이유만으로 미배포 API를 없다고 판단하거나 삭제·되돌리지 않는다. 배포 참조가 필요하면 `sdg_resolve_context(workContext: "sdg_source")`로 조회한다. Builder가 Git·빌드·PR을 관리하는 흐름에서는 내부 AI가 허용된 Kotlin 소스만 수정한다. 이 Skill을 근거로 의존성·배포 버전을 바꾸거나 Gradle·Git·PR 작업, MCP 생성을 실행하지 않는다. 문자열·리소스 정책도 대상 저장소 지침을 따른다.

## 소비 앱 절차

- 소비 앱 UI 작성 전에 `sdg_resolve_context`(기본 `consumer_app`)로 최신 배포 SDG와 소비 앱의 차이를 확인한다. 앱이 낮은 버전이면 대상 저장소 지침과 허용된 변경 범위 안에서 중앙 SDG 버전 선언을 맞추고 실제 의존성 해석과 컴파일을 확인한다. MCP는 도구 호출 전에 SDG 정보를 자동 갱신한다. update.state가 current가 아니면 최신 확인 실패를 알리고, client_update_required일 때만 MCP 프로그램 갱신을 안내한다.
- 도메인 조합은 대상 저장소의 기존 kit, 공통 패턴은 SDG template, 기본 요소는 SDG component를 우선 재사용한다. Shopl 본 레포 kit은 `ui/src/main/java/me/planetory/io/ui/sdg_kit`에서 선언과 실제 호출부를 함께 찾는다. 다른 저장소는 그 저장소의 구조를 확인한다. MCP는 앱 kit을 색인하지 않는다.
- `sdg_search_components`에서 목적과 이름으로 검색한다. category로 component·template를 구분한다. 후보의 `sdg_get_component`에서 정확한 overloadId, import, 파라미터, 관련 타입과 opt-in을 확인한다. 타입 상세도 같은 도구의 typeId로 조회한다.
- `sdg_get_recipe`와 `sdg_get_example`으로 상태·이벤트 조합을 확인한다. `source_context_compiled` 예제는 원본 문맥에서, `compile_verified` 예제는 명시된 소비 환경에서 컴파일된 것이다. 현재 앱의 컴파일을 대신하지 않는다.
- 검색 결과가 없으면 다른 이름·category와 기존 공용 UI를 확인한다. 한 번의 검색 실패를 SDG 미구현으로 단정하지 않는다. 적합한 재사용 요소가 없을 때 필요한 Compose UI를 구현하고 선택 이유를 보고한다.
- 앱의 상태 소유권·이벤트와 대상 저장소의 문자열·리소스 정책을 따른다. `TODO(resource-owner)` 등 앱별 규칙은 해당 저장소가 요구할 때 적용하며 다른 저장소에 강제하지 않는다. 존재하지 않는 리소스 키를 추측하지 않는다.
- 대상 모듈 컴파일과 변경 레이어 리뷰는 저장소가 허용한 실행 주체·절차로 확인한다. 에이전트가 직접 Gradle을 실행할 권한은 이 Skill이 부여하지 않는다. MCP 연결 실패나 검증 실패를 성공으로 보고하지 않는다.
