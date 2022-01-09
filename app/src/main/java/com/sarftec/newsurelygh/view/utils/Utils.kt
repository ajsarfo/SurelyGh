package com.sarftec.newsurelygh.view.utils

import org.apache.commons.text.StringEscapeUtils

fun String.parseFromUTF() : String {
    return StringEscapeUtils.unescapeHtml4(this)
}