package app.gamenative.service

import android.content.Context
import com.luatools.javasteam.LuaToolsBridgeProvider
import com.luatools.javasteam.LuaToolsStore
import java.io.File

/**
 * GameNative-specific bridge provider for LuaTools Android & CloudRedirect.
 * Exposes `content://app.gamenative.bh_lua_bridge` to receive `.lua`, `.manifest`,
 * game fixes, and CloudRedirect save sync requests.
 */
class GameNativeLuaBridgeProvider : LuaToolsBridgeProvider() {

    override fun getAppDisplayName(ctx: Context): String = "GameNative"

    override fun resolveGameInstallDir(ctx: Context, appId: Int, fallbackName: String): File {
        if (SteamService.instance != null) {
            val path = SteamService.getAppDirPath(appId)
            if (path.isNotBlank()) return File(path)
        }
        return super.resolveGameInstallDir(ctx, appId, fallbackName)
    }

    override fun onGameAdded(ctx: Context, appId: Int, name: String, store: LuaToolsStore) {
        SteamService.syncLuaToolsApp(ctx.applicationContext, appId)
    }
}
