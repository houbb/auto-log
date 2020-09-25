package com.github.houbb.auto.log.core.bs;

import com.github.houbb.auto.log.api.IAutoLog;
import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.core.core.impl.SimpleAutoLog;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public final class AutoLogBs {

    private static final Log LOG = LogFactory.getLog(AutoLogBs.class);

    private AutoLogBs() {
    }

    /**
     * 上下文
     * @since 0.0.7
     */
    private IAutoLogContext context;

    /**
     * 实现类
     * @since 0.0.7
     */
    private Class<? extends IAutoLog> autoLogClass = SimpleAutoLog.class;

    /**
     * 新建对象实例
     * @return this
     * @since 0.0.3
     */
    public static AutoLogBs newInstance() {
        return new AutoLogBs();
    }

    /**
     * 设置实现类
     * @param autoLogClass 实现类
     * @return this
     * @since 0.0.9
     */
    public AutoLogBs autoLogClass(Class<? extends IAutoLog> autoLogClass) {
        ArgUtil.notNull(autoLogClass, "autoLogClass");
        this.autoLogClass = autoLogClass;
        return this;
    }

    public IAutoLogContext context() {
        return context;
    }

    public AutoLogBs context(IAutoLogContext context) {
        this.context = context;
        return this;
    }

    /**
     * 自定日志输出
     * @return 输出
     * @since 0.0.6
     */
    public Object execute() throws Throwable {
        IAutoLog autoLog = ClassUtil.newInstance(autoLogClass);
        return autoLog.autoLog(context);
    }

}
