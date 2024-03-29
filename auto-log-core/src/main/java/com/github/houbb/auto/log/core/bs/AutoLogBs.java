package com.github.houbb.auto.log.core.bs;

import com.github.houbb.auto.log.api.IAutoLog;
import com.github.houbb.auto.log.api.IAutoLogContext;
import com.github.houbb.auto.log.api.IAutoLogObjectHandler;
import com.github.houbb.auto.log.core.core.impl.SimpleAutoLog;
import com.github.houbb.auto.log.core.support.handler.AutoLogObjectHandlers;
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
    private IAutoLog autoLog = new SimpleAutoLog();

    /**
     * @since 0.10.0
     */
    private IAutoLogObjectHandler autoLogObjectHandler = AutoLogObjectHandlers.defaults();

    /**
     * 丢弃的大小限制
     * @since 0.10.0
     */
    private int discardSizeLimit = 1001;

    /**
     * 最长的日志长度
     * @since 0.11.0
     */
    private int maxLogLen = 65535;

    /**
     * 新建对象实例
     * @return this
     * @since 0.0.3
     */
    public static AutoLogBs newInstance() {
        return new AutoLogBs();
    }

    public AutoLogBs discardSizeLimit(int discardSizeLimit) {
        this.discardSizeLimit = discardSizeLimit;
        return this;
    }

    public AutoLogBs autoLog(IAutoLog autoLog) {
        ArgUtil.notNull(autoLog, "autoLog");

        this.autoLog = autoLog;
        return this;
    }

    public IAutoLogContext context() {
        return context;
    }

    public AutoLogBs context(IAutoLogContext context) {
        this.context = context;
        return this;
    }

    public AutoLogBs autoLogObjectHandler(IAutoLogObjectHandler autoLogObjectHandler) {
        ArgUtil.notNull(autoLogObjectHandler, "autoLogObjectHandler");

        this.autoLogObjectHandler = autoLogObjectHandler;
        return this;
    }

    public AutoLogBs maxLogLen(int maxLogLen) {
        this.maxLogLen = maxLogLen;
        return this;
    }

    /**
     * 自定日志输出
     * @return 输出
     * @since 0.0.6
     * @throws Throwable 执行异常
     */
    public Object execute() throws Throwable {
        context.autoLogObjectHandler(autoLogObjectHandler);
        context.discardSizeLimit(discardSizeLimit);
        context.maxLogLen(maxLogLen);
        return autoLog.autoLog(context);
    }

}
