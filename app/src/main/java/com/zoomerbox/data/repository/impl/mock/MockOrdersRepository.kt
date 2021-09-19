package com.zoomerbox.data.repository.impl.mock

import com.zoomerbox.data.repository.IOrdersRepository
import com.zoomerbox.data.repository.impl.mock.utils.MockDataProvider
import com.zoomerbox.model.enumeration.OrderStatusEnum
import com.zoomerbox.model.app.Order
import com.zoomerbox.model.app.OrderBox
import com.zoomerbox.model.app.ZoomerBox

class MockOrdersRepository : IOrdersRepository {

    override fun getOrders(): List<Order> {
        val orderBoxes1 = MockDataProvider.getBoxes(listOf("X_MARVEL", "X_DC"))
            .map { box -> OrderBox(ZoomerBox.buildFromDTO(box), 3, true) }
        val orderBoxes2 = MockDataProvider.getBoxes(listOf("X_GUCCI", "X_PRADA"))
            .map { box -> OrderBox(ZoomerBox.buildFromDTO(box), 3, false) }
        return listOf(
            Order(orderBoxes1, "24 июля, 1902", "ZOOMERO-0000-0001-2407", OrderStatusEnum.CREATED, 6969),
            Order(orderBoxes2, "2 января, 1802", "ZOOMERO-0000-0001-1808", OrderStatusEnum.PACKAGED, 6969)
        )
    }

    override fun createOrder(
        cityName: String,
        fullName: String,
        postIndex: String,
        orderItems: List<OrderBox>
    ): Order {
        return Order(orderItems, "24.08.1999", "1234-1234-1234", OrderStatusEnum.SENT, 300)
    }

    override fun getImplName(): String {
        return this::class.java.simpleName
    }
}
