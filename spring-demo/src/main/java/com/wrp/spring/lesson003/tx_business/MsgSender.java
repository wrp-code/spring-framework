package com.wrp.spring.lesson003.tx_business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 消息发送器
 * @author wrp
 * @since 2025-04-29 08:10
 **/
@Component
public class MsgSender {

	@Autowired
	private MsgOrderService msgOrderService;
	@Autowired
	private MsgService msgService;

	//发送消息
	public void send(String msg, int ref_type, String ref_id) {
		MsgOrderModel msgOrderModel = this.msgOrderService.insert(ref_type, ref_id);

		Long msg_order_id = msgOrderModel.getId();
		//TransactionSynchronizationManager.isSynchronizationActive 可以用来判断事务同步是否开启了
		boolean isSynchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
		/**
		 * 若事务同步开启了，那么可以在事务同步中添加事务扩展点，则先插入消息，暂不发送，则在事务扩展点中添加回调
		 * 事务结束之后会自动回调扩展点TransactionSynchronizationAdapter的afterCompletion()方法
		 * 咱们在这个方法中确定是否投递消息
		 */
		if (isSynchronizationActive) {
			final Long msg_id = this.msgService.addMsg(msg, msg_order_id, false);
			TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
				@Override
				public void afterCompletion(int status) {
					//代码走到这里时，事务已经完成了（可能是回滚了、或者是提交了）
					//看一下消息关联的订单是否存在，如果存在，说明事务是成功的，业务是执行成功的，那么投递消息
					if (msgOrderService.getById(msg_order_id) != null) {
						System.out.println(String.format("准备投递消息,{msg_id:%s}", msg_id));
						//事务成功：投递消息
						msgService.confirmSendMsg(msg_id);
					} else {
						System.out.println(String.format("准备取消投递消息，{msg_id:%s}", msg_id));
						//事务是不：取消投递消息
						msgService.cancelSendMsg(msg_id);
					}
				}
			});
		} else {
			//无事务的，直接插入并投递消息
			this.msgService.addMsg(msg, msg_order_id, true);
		}
	}
}
