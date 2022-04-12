import { Orderline } from "./Orderline";

export class WebSocketMessage {
    userId!: string;
    orderId!: string;
    orderLines!: Array<Orderline>;
    orderLineDTO!: Orderline;
    finished!: boolean;
    productId!: string;
    price!: number;
    productName!: string;
    productDescr!: string;
}
