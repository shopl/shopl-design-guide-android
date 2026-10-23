package com.shopl.sdg.component.icon_label_builder_test

enum class SDGIconLabelBuilderTestType(
    internal val fontWeight: SDGIconLabelBuilderTestFontWeight,
) {
    Normal(
        fontWeight = SDGIconLabelBuilderTestFontWeight.Normal,
    ),
    Bold(
        fontWeight = SDGIconLabelBuilderTestFontWeight.Bold,
    ),
    ;

    companion object {
        @Deprecated(
            message = "Basic 대신 Normal을 사용하세요.",
            replaceWith = ReplaceWith("Normal"),
        )
        @Suppress("PropertyName")
        val Basic: SDGIconLabelBuilderTestType = Normal

        @Deprecated(
            message = "Empha 대신 Bold를 사용하세요.",
            replaceWith = ReplaceWith("Bold"),
        )
        @Suppress("PropertyName")
        val Empha: SDGIconLabelBuilderTestType = Bold
    }
}
