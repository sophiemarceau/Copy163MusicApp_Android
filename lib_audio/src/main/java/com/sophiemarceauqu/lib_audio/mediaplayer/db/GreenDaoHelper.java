package com.sophiemarceauqu.lib_audio.mediaplayer.db;

import android.database.sqlite.SQLiteDatabase;

import com.sophiemarceauqu.lib_audio.app.AudioHelper;
import com.sophiemarceauqu.lib_audio.mediaplayer.model.AudioBean;
import com.sophiemarceauqu.lib_audio.mediaplayer.model.Favourite;

/**
 * 操作greenDao数据库帮助类
 * 它的体积非常小 并且 它的api几乎是好 并且是面向对象的 不用对面sql
 */
public class GreenDaoHelper {
    private static final String DB_NAME = "music_db";
    //用来创建数据库，升级数据库
    private static DaoMaster.DevOpenHelper mHelper;//annotation 帮我们生成的类，如果没有实体类是不会去创建的
    //最终创建好的数
    private static SQLiteDatabase mDb;
    //管理数据库
    private static DaoMaster mDaoMaster;
    //管理各种实体Dao，不让业务层拿到session直接去操作数据库，统一有此类提供方法
    private static DaoSession mDaoSession;

    /**
     * 设置greenDao
     */
    public static void initDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(AudioHelper.getContext(), DB_NAME, null);
        mDb = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDb);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * 添加感兴趣
     *
     * @param audioBean
     */
    public static void addFavourite(AudioBean audioBean) {
        FavouriteDao dao = mDaoSession.getFavouriteDao();
        Favourite favourite = new Favourite();
        favourite.setAudioId(audioBean.id);
        favourite.setAudioBean(audioBean);
        dao.insertOrReplace(favourite);
    }

    /**
     * 移除感兴趣
     */
    public static void removeFavourite(AudioBean audioBean) {
        FavouriteDao dao = mDaoSession.getFavouriteDao();
        Favourite favourite = dao.queryBuilder()
                .where(FavouriteDao.Properties.AudioId.eq(audioBean.id)).unique();
        dao.delete(favourite);
    }

    /**
     * 查找感兴趣
     */
    public static Favourite selectFavourite(AudioBean audioBean) {
        FavouriteDao dao = mDaoSession.getFavouriteDao();
        Favourite favourite = dao.queryBuilder().where(FavouriteDao.Properties.AudioId.eq(audioBean.id)).unique();
        return favourite;
    }
}
