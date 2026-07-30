package com.shopl.sdg.component.icon_label

enum class SDGIconLabelType(
    internal val fontWeight: SDGIconLabelFontWeight,
) {
    Normal(
        fontWeight = SDGIconLabelFontWeight.Normal,
    ),
    Bold(
        fontWeight = SDGIconLabelFontWeight.Bold,
    ),
    ;

    companion object {
        @Deprecated(
            message = "Basic 대신 Normal을 사용하세요.",
            replaceWith = ReplaceWith("Normal"),
        )
        @Suppress("PropertyName")
        val Basic: SDGIconLabelType = Normal

        @Deprecated(
            message = "Empha 대신 Bold를 사용하세요.",
            replaceWith = ReplaceWith("Bold"),
        )
        @Suppress("PropertyName")
        val Empha: SDGIconLabelType = Bold
    }
}
