---
name: sdg-android-components
description: Shopl Android 또는 SDG를 사용하는 Compose 화면을 새로 만들거나 수정할 때 사용한다. 사용자가 SDG를 명시하지 않아도 component·template·앱 kit 재사용 후보와 정확한 API를 확인한다.
---

# Android SDG 화면 구현

- UI 작성 전에 `sdg_resolve_context`로 최신 배포 SDG와 소비 앱의 차이를 확인한다. 앱이 낮은 버전이면 중앙 SDG 버전 선언을 맞추고 실제 의존성 해석과 컴파일을 확인한다. MCP는 도구 호출 전에 SDG 정보를 자동 갱신한다. update.state가 current가 아니면 최신 확인 실패를 알리고, client_update_required일 때만 MCP 프로그램 갱신을 안내한다.
- 도메인 조합은 대상 저장소의 기존 kit, 공통 패턴은 SDG template, 기본 요소는 SDG component를 우선 재사용한다. Shopl 본 레포 kit은 `ui/src/main/java/me/planetory/io/ui/sdg_kit`에서 선언과 실제 호출부를 함께 찾는다. 다른 저장소는 그 저장소의 구조를 확인한다. MCP는 앱 kit을 색인하지 않는다.
- `sdg_search_components`에서 목적과 이름으로 검색한다. category로 component·template를 구분한다. 후보의 `sdg_get_component`에서 정확한 overloadId, import, 파라미터, 관련 타입과 opt-in을 확인한다. 타입 상세도 같은 도구의 typeId로 조회한다.
- `sdg_get_recipe`와 `sdg_get_example`으로 상태·이벤트 조합을 확인한다. `source_context_compiled` 예제는 원본 문맥에서, `compile_verified` 예제는 명시된 소비 환경에서 컴파일된 것이다. 현재 앱의 컴파일을 대신하지 않는다.
- 검색 결과가 없으면 다른 이름·category와 기존 공용 UI를 확인한다. 한 번의 검색 실패를 SDG 미구현으로 단정하지 않는다. 적합한 재사용 요소가 없을 때 필요한 Compose UI를 구현하고 선택 이유를 보고한다.
- 앱의 상태 소유권·이벤트·문자열 정책을 유지한다. 문자열 리소스 직접 생성·수정, 존재하지 않는 키와 임의 하드코딩으로 우회하지 않는다. 필요한 정확한 문구와 전용 키를 `TODO(resource-owner)`로 사용 지점에 남긴다.
- 대상 모듈 컴파일과 저장소가 요구하는 변경 레이어 리뷰를 수행한다. MCP 연결 실패나 검증 실패를 성공으로 보고하지 않는다.
