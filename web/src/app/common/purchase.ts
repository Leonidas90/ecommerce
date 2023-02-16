import { Address } from "./address";
import { Customer } from "./customer";
import { Order } from "./order";
import { OrderItem } from "./order-item";

export class Purchase {

    customer: Customer;
    shippingAddress: Address;
    order: Order;
    orderItems: OrderItem[];

    constructor(customer: Customer,shippingAddress: Address,order: Order,orderItems: OrderItem[]) {
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.order = order;
        this.orderItems = orderItems;

    }
}
