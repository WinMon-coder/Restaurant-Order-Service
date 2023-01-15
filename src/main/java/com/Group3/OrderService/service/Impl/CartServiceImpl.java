//package com.Group3.OrderService.service.Impl;
//
//import java.util.List;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.Group3.OrderService.entity.Cart;
//import com.Group3.OrderService.entity.Category;
//import com.Group3.OrderService.repository.CartRepository;
//import com.Group3.OrderService.service.CartService;
//@Service
//public class CartServiceImpl implements CartService{
//@Autowired
//CartRepository cartRepository;
//	@Override
//	public Cart createCart(Cart cart) {
//		return cartRepository.save(cart);
//	}
//
//	@Override
//	public List<Cart> getAllCarts() {
//		return cartRepository.findAll();
//	}
//
//	@Override
//	public Cart getCart(int id) {
//		return cartRepository.findById(id).orElse(null);
//	}
//
//	@Override
//	public Cart updateCart(int id, Cart cart) {
//		Cart orginalCart = this.getCart(id);
//		if (orginalCart == null) {
//			return null;
//		}
//		orginalCart.setCartId(id);
//		orginalCart.setFood(cart.getFood());
//		orginalCart.setQuantity(cart.getQuantity());
//		orginalCart = cartRepository.save(orginalCart);
//
//		return orginalCart;	}
//
//	@Override
//	public Boolean deleteCart(int id) {
//		Cart cart = this.getCart(id);
//		if (cart == null) {
//			return false;
//		}
//		cartRepository.deleteById(id);
//		return true;
//	}
//
//}
