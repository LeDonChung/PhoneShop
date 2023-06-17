package com.phone.library.constants;

public enum OrderStatus {
    cancel, // đã hủy
    pending, // chờ xác nhận
    completed, // hoàn thành
    shipped, // đang giao hàng
    awaiting_shipment, //  đang đợi giao hàng --> đã xác nhận
    declined // người bán từ chối giao hàng
}
