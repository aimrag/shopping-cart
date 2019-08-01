### Design Notes
- Assumed that coupons are also a type of a discount as campaigns.
- Factory classes are used to produce discount classes in order to hide the implementation details from application. 
Application class is only responsible for producing these classess. 
- Initialization of discount objects are different than the case document because of factory classes.
- Maximum discount of any campaign is applied to the cart as stated in the case doc.
- Cart print method prints related information after grouping categories.
- Testcases are added.
- Javadoc is added.

##### Discount Initialization:
```sh
ICampaign campaign1 = DiscountProducer.getCampignFactory(DiscountType.RATE, food, 20.0, 3);
ICampaign campaign2 = DiscountProducer.getCampignFactory(DiscountType.AMOUNT, food, 90.0, 3);	
```
