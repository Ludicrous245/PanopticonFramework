package com.ludicrous245

import api.ludicrous245.project.SubProject


class PanopticonSubPlugin: SubProject("Sub") {
    override fun onLoad() {
        log("서브")
    }
}