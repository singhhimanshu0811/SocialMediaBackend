# imp 

## adding product in a cart and differing definitions of cart and cart dto


1. If you weren't mutating the object inside the lambda (e.g., you were directly mapping values to create a new object in a single expression), you could avoid using return and {} entirely.

For example:

cartDTO.setProducts(
cartItems.stream()
.map(item -> modelMapper.map(item.getProduct(), ProductDTO.class))
.toList()
);

Here, the lambda simply maps each item.getProduct() to a ProductDTO using modelMapper, and no explicit return or curly braces are needed because there's no additional logic.
basically creating an object and modifying it, you need to use return inside {}

2. we are doing all of this because we want to convert cart item list in product list
3. we are explicitly setting quantity of product dto from cart item in point 1(because of which we have to use return) , that is because, the context of quantity of cart item is different from that of quantity of product.
we are not updating product quantity here , but in some cases we might delete the quantity even when you add in cart, and then this step becomes critical, where in cart dto, rather than quantity of product, you need quantity of cart item, so you extract quantity of cart iteam, and feed in quanity of product dto, finally going in a list.