package shopping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import shopping.model.Discount;
import shopping.model.ItemRequired;
import shopping.model.Product;

public class CalculatePrice {
	

	public static void main(String[] args) throws IOException {

		List<Product> productList = new ArrayList<>();
		List<Discount> discountList = new ArrayList<>();
		List<ItemRequired> items = new ArrayList<>();
		

		createStaticData(productList, discountList);
		int dayOfPurchase = getInput(productList, items);
		calculatePrice(productList, discountList, items, dayOfPurchase);

	}

	private static void calculatePrice(List<Product> productList, List<Discount> discountList, List<ItemRequired> items,
			int dayOfPurchase) {

		Double cost = 0.0;

		List<String> discountProductList = discountList.stream().map(Discount::getProductName)
				.collect(Collectors.toList());
		// find discounted items
		//
		Iterator<ItemRequired> itr = items.iterator();
		List<String> removeItem = new ArrayList<>();
		while (itr.hasNext()) {

			ItemRequired item = itr.next();
			if (!removeItem.contains(item.getProductName())) {
				Product thisProduct = productList.stream()
						.filter(p -> p.getProductName().equalsIgnoreCase(item.getProductName())).findAny().get();

				if (discountProductList.contains(item.getProductName())) {
					Discount thisOffer = discountList.stream()
							.filter(o -> o.getProductName().equalsIgnoreCase(item.getProductName())).findAny().get();

					if (dayOfPurchase <= thisOffer.getValidityEndDay()
							&& dayOfPurchase >= thisOffer.getValidityStartDay()
							&& thisOffer.getDiscountType().equalsIgnoreCase("self")) {
						item.setCost(item.getQty() * thisProduct.getCost() * (100 - thisOffer.getDiscountPercentage()) / 100);

					} else if (dayOfPurchase <= thisOffer.getValidityEndDay()
							&& dayOfPurchase >= thisOffer.getValidityStartDay()
							&& thisOffer.getDiscountType().equalsIgnoreCase("other")) {

						calculateNonSelfDiscountCost(item, thisProduct, items, thisOffer, removeItem, productList);
					}

				} else {
					item.setCost(item.getQty() * thisProduct.getCost());
				}

				cost += item.getCost();
			}
		}

		NumberFormat format = new DecimalFormat("#.##");
		System.out.println("Estimated total cost : " + format.format(cost));

	}

	private static void calculateNonSelfDiscountCost(ItemRequired item, Product thisProduct, List<ItemRequired> items,
			Discount thisOffer, List<String> removeItem, List<Product> productList) {

		item.setCost(item.getQty() * thisProduct.getCost());

		if (item.getQty() >= thisOffer.getProductQuantity()) {
			Product disProduct = productList.stream()
					.filter(p -> p.getProductName().equalsIgnoreCase(thisOffer.getProductName())).findAny().get();

			Optional<ItemRequired> disItem = items.stream()
					.filter(i -> i.getProductName().equalsIgnoreCase(thisOffer.getDiscountOn())).findFirst();

			if (disItem.isPresent()) {
				ItemRequired discountItem = disItem.get();

				if (discountItem.getQty() == thisOffer.getDiscountQuantity()) {
					item.setCost(item.getCost()
							+ (discountItem.getQty() * disProduct.getCost() * (100 - thisOffer.getDiscountPercentage()) / 100));
				} else if (discountItem.getQty() > thisOffer.getDiscountQuantity()) {
					item.setCost(item.getCost() + (thisOffer.getDiscountQuantity() * disProduct.getCost()
							* (100 - thisOffer.getDiscountPercentage()) / 100));
					item.setCost((discountItem.getQty() - thisOffer.getDiscountQuantity()) * disProduct.getCost());
				} else if (discountItem.getQty() < thisOffer.getDiscountQuantity()) {
					item.setCost(discountItem.getQty() * disProduct.getCost());
				}
				removeItem.add(discountItem.getProductName());
			}
		}

	}

	private static int getInput(List<Product> productList, List<ItemRequired> items)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		List<String> productNameList = new ArrayList<>();
		int dayOfPurchase =1;

		productList.forEach(p -> {
			System.out.println(p.getProductName());
			productNameList.add(p.getProductName().toLowerCase());
		});

		System.out.println("Please input count of items to be purchased from the given list");
		int count = Integer.parseInt(bufferedReader.readLine().trim());

		for (int i = 0; i < count; i++) {
			System.out.println("Please input product name : ");

			String product = bufferedReader.readLine().trim();

			if (!productNameList.contains(product.toLowerCase())) {
				System.out.println("Product is unavailable. Please input a Product name from the list above: ");
			} else {
				System.out.println("Please input quantity required : ");
				int qty = Integer.parseInt(bufferedReader.readLine().trim());
				if (qty > 0) {
					items.add(new ItemRequired(product, qty));
				} else {
					System.out.println("Invalid quantity");
				}

			}

		}
		System.out.println("Please enter in how many days the purchase to be made. Enter 1 for today.");
		dayOfPurchase = Integer.parseInt(bufferedReader.readLine().trim());

		bufferedReader.close();
		return dayOfPurchase;
	}

	private static void createStaticData(List<Product> productList, List<Discount> discountList) {

		Discount discount = new Discount();
		Discount appleDiscount = new Discount();

		discount.setProductName("soup");
		discount.setProductQuantity((long) 2);
		discount.setDiscountType("other");
		discount.setDiscountOn("bread");
		discount.setDiscountQuantity((long) 1);
		discount.setDiscountPercentage((long) 50);
		discount.setValidityStartDay((long) 0);
		discount.setValidityEndDay((long) 7);

		appleDiscount.setProductName("apples");
		appleDiscount.setDiscountType("self");
		appleDiscount.setDiscountPercentage((long) 10);
		appleDiscount.setValidityStartDay((long) 4);
		appleDiscount.setValidityEndDay((long) 27);

		discountList.add(discount);
		discountList.add(appleDiscount);

		productList.add(new Product("soup", 0.65));
		productList.add(new Product("apples", 0.10));
		productList.add(new Product("bread", 0.80));
		productList.add(new Product("milk", 1.30));
	}

}
