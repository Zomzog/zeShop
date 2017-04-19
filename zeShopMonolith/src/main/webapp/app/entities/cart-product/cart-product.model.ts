export class CartProduct {
    constructor(
        public id?: number,
        public quantity?: number,
        public addedDate?: any,
        public cartId?: number,
        public productId?: number,
        public productCustomizationId?: number,
    ) {
    }
}
