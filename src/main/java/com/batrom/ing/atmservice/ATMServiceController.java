//package com.batrom.ing.atmservice;
//
//import io.javalin.http.Handler;
//
//public class ATMServiceController {
//
//    public static Handler fetchAllUsernames = ctx -> {
//        UserDao dao = UserDao.instance();
//        Iterable<String> allUsers = dao.getAllUsernames();
//        ctx.json(allUsers);
//    };
//
//    public static Handler fetchById = ctx -> {
//
//        int id = Integer.parseInt(Objects.requireNonNull(ctx.param("id")));
//        UserDao dao = UserDao.instance();
//        Optional<User> user = dao.getUserById(id);
//        if (user.isPresent()) {
//            ctx.json(user);
//        } else {
//            ctx.html("Not Found");
//        }
//    };
//}
