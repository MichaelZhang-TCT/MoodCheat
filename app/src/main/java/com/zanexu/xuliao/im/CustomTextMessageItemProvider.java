package com.zanexu.xuliao.im;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.message.TextMessage;

/**
 * custom the text in cheat ui
 * Created by zanexu on 2017/7/9.
 */

@ProviderTag(messageContent = TextMessage.class, showSummaryWithName = false)
public class CustomTextMessageItemProvider extends TextMessageItemProvider {
}
