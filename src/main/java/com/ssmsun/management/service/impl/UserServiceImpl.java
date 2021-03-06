package com.ssmsun.management.service.impl;


import com.ssmsun.management.dao.UserMapper;
import com.ssmsun.management.entity.User;
import com.ssmsun.management.global.exception.*;
import com.ssmsun.management.service.UserService;
import com.ssmsun.management.util.encryption.EncryptedPassword;
import com.ssmsun.management.util.excel.ExportExcel;
import com.ssmsun.management.util.verify.code.EmailCode;
import com.ssmsun.management.util.verify.code.RegCode;
import com.ssmsun.management.util.verify.token.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RegCode regCode;

    @Autowired
    EmailCode emailCode;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    EncryptedPassword encryptedPassword;

    @Autowired
    ExportExcel exportExcel;

    private String aliyunCode;
    private String aliyunCode2;
    private String mailCode;


    private static final long FILE_MAX_SIZE = 1000 * 1024;
    private static final List<String> FILE_TYPES = new ArrayList<>();

    static {
        FILE_TYPES.add("image/jpeg");
        FILE_TYPES.add("image/png");
    }

    @Override
    public void phoneCode(String phone) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            String ran = String.valueOf((int) (Math.random() * 9 + 1));
            str.append(ran);
        }
        aliyunCode = str.toString();
        regCode.sendCode(phone, aliyunCode);
    }

    @Override
    public void phoneCode2(Integer userid) throws UserNotFoundException {
        User result = userMapper.person(userid);
        if (result == null) {
            throw new UserNotFoundException("用户不存在!");
        }
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            String ran = String.valueOf((int) (Math.random() * 9 + 1));
            str.append(ran);
        }
        aliyunCode2 = str.toString();
        regCode.sendCode(result.getPhone(), aliyunCode2);
    }

    @Override
    public void emailCode(Integer userid) throws MessagingException {
        User result = userMapper.person(userid);
        StringBuilder builder = new StringBuilder();
        String charStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * charStr.length());
            builder.append(charStr.charAt(index));
        }
        mailCode = builder.toString();
        emailCode.sendMail(result.getEmail(), mailCode);
    }

    @Override
    public void regUser(User user, String code) throws Exception {
        if (aliyunCode == null) {
            throw new VerifyGetFailException("获取验证码失败!");
        }
        if (!aliyunCode.equals(code)) {
            throw new VerifyNotMatchException("验证码错误!");
        }
        User result = userMapper.getUserByUsername(user.getUsername());
        if (result != null) {
            throw new UsernameRepeatException("用户名已存在！");
        }
        String saltUUID = UUID.randomUUID().toString().toUpperCase();
        String SHA256Pwd = encryptedPassword.getPassword(user.getPassword(), saltUUID);
        System.out.println("reg:" + SHA256Pwd);
        user.setPassword(SHA256Pwd);
        user.setSalt(saltUUID);
        user.setAmount(0.00);
        user.setAvatar("d5061dba0c9a45efb83abbab34981cfc.jpg");
        user.setCredate(LocalDateTime.now());
        user.setConfirm(0);
        user.setVip(0);
        Integer row = userMapper.addUser(user);
        if (row != 1) {
            throw new InsertUserException("新增用户失败！");
        }
    }

    @Override
    public String login(String username, String password) throws Exception {
        User result = userMapper.getUserByUsername(username);
        if (result == null) {
            throw new UserNotFoundException("用户不存在!");
        }
        if (!(encryptedPassword.getPassword(password, result.getSalt())).equals(result.getPassword())) {
            throw new PasswordNotMatchException("密码错误");
        }
        String key = UUID.randomUUID().toString();
        String value = jwtUtil.createToken(String.valueOf(result.getUserid()));
        redisTemplate.opsForValue().set(key, value, 30, TimeUnit.MINUTES);
        return key;
    }

    @Override
    public List<User> userInfo(Integer page) throws UserNotFoundException {
        List<User> users = userMapper.paging((page - 1) * 20);
        if (users == null) {
            throw new UserNotFoundException("获取用户信息失败！");
        }
        return users;
    }

    @Override
    public Integer userTotal() {
        Integer total = userMapper.getUserTotal();
        return total;
    }

    @Override
    public User person(Integer userid) throws UserNotFoundException {
        User result = userMapper.person(userid);
        if (result == null) {
            throw new UserNotFoundException("查询信息失败!");
        }
        return result;
    }

    @Override
    public void userConfirm(Integer userid, String code) throws Exception {
        if (mailCode == null) {
            throw new VerifyGetFailException("获取验证码失败!");
        }
        if (!mailCode.equals(code)) {
            throw new VerifyNotMatchException("验证码错误!");
        }
        Integer row = userMapper.changeStatus(userid);
        if (row != 1) {
            throw new UpdateUserException("验证失败！");
        }
    }

    @Override
    public void updatePassword(Integer userid, String password, String newPwd, String code) throws Exception {
        if (aliyunCode2 == null) {
            throw new VerifyGetFailException("获取验证码失败!");
        }
        if (!aliyunCode2.equals(code)) {
            throw new VerifyNotMatchException("验证码错误!");
        }
        User result = userMapper.person(userid);
        if (result == null) {
            throw new UserNotFoundException("用户不存在!");
        }
        String oldPwd = encryptedPassword.getPassword(password, result.getSalt());
        if (!result.getPassword().equals(oldPwd)) {
            throw new PasswordNotMatchException("密码不匹配！");
        }
        Integer row = userMapper.changePwd(userid, encryptedPassword.getPassword(newPwd, result.getSalt()));
        if (row != 1) {
            throw new UpdateUserException("修改密码失败！");
        }
    }


    @Override
    public void updateInfo(Integer userid, User user) throws Exception {
        User result = userMapper.person(userid);
        if (result == null) {
            throw new UserNotFoundException("用户未找到！");
        }
        Integer row = userMapper.changeInfoByUserid(userid, user.getEmail(), user.getPhone(), user.getGender());
        if (row != 1) {
            throw new UpdateUserException("更新用户信息失败！");
        }
    }

    @Override
    public String updateAvatar(Integer userid, MultipartFile file) throws Exception {
        User result = userMapper.person(userid);
        if (result.getConfirm() == 0) {
            throw new UnConfirmException("未验证邮箱不能修改信息！");
        }
        if (file.isEmpty()) {
            throw new FileEmptyException("请选择文件!");
        }
        if (file.getSize() > (FILE_MAX_SIZE)) {
            throw new FileSizeException("文件不能超过" + (FILE_MAX_SIZE / 1024) + "KB!");
        }
        if (!FILE_TYPES.contains(file.getContentType())) {
            throw new FileTypeException("不支持的格式!");
        }
//        String path = ResourceUtils.getURL("classpath:").getPath()+"/picture";
        String path = "E:\\IdeaProject\\User Management System\\src\\main\\resources\\static\\picture";
//        String path = "/home/picture";
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        int index = originalFilename.lastIndexOf(".");
        if (index > 0) {
            suffix = originalFilename.substring(index);
        }
        String fileName = UUID.randomUUID().toString() + suffix;
        File dest = new File(directory, fileName);
        try {
            file.transferTo(dest);
            Integer row = userMapper.changeAvatar(userid, fileName);
            if (row != 1) {
                throw new UpdateUserException("修改头像出现错误！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadIOException("上传文件时出现读写错误!");
        }
        return fileName;
    }

    @Override
    public void subUser(Integer userid) throws Exception {
        User result = userMapper.person(userid);
        if (result == null) {
            throw new UserNotFoundException("用户未找到！");
        }
        Integer row = userMapper.delUserByid(userid);
        if (row != 1) {
            throw new DeleteUserException("删除失败！");
        }
    }

    @Override
    public void downLoadUserData(HttpServletResponse response) throws Exception {
        List<User> users = userMapper.getAllUser();
        if (users == null) {
            throw new UserNotFoundException("获取用户信息失败！");
        }
        exportExcel.UserExcel(users, response);
    }
}
