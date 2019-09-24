package com.wangtao.moretolearn;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动管理器，通过List来暂存活动。
 * 可以添加、移除以及移除所有活动方法
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    /**
     * 添加一个活动到List中
     * @param activity 需要添加的活动
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 移除List中一个活动
     * @param activity 需要移除的活动
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 移除List中所有的活动
     * @return 所有活动移除成功返回true，否则返回false
     */
    public static boolean finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        return activities.size() == 0;
    }

}
