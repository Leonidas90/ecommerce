export class Order {

    totalQuantity: number;
    totalPrice: number;

    constructor(totalQuantity: number,totalPrice: number) {
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }
}
