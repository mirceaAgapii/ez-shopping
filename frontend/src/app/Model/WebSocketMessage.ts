import { Orderline } from "./Orderline";

export class WebSocketMessage {
    userId!: string;
    orderId!: string;
    orderLines!: Array<Orderline>;
    orderLineDTO!: Orderline;
    totalQty!: number;
    finished!: boolean;
    productId!: string;
    price!: number;
    productName!: string;
    productDescr!: string;
}