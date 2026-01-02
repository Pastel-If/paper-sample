package io.github.pastelif.sample.plugin

import org.bukkit.plugin.java.JavaPlugin

class SamplePlugin : JavaPlugin() {

    override fun onEnable() {
        logger.info("샘플 플러그인이 활성화 되었습니다!")
    }

    override fun onDisable() {
        logger.info("샘플 플러그인이 비활성화 되었습니다!")
    }
}
