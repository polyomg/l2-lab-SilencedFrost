package com.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParamService {
    private final HttpServletRequest request;

    /**
     * Đọc chuỗi giá trị của tham số
     * @param name tên tham số
     * @param defaultValue giá trị mặc định
     * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
     */
    public String getString(String name, String defaultValue){
        return Objects.requireNonNullElse(request.getParameter(name), defaultValue);
    }

    /**
     * Đọc số nguyên giá trị của tham số
     * @param name tên tham số
     * @param defaultValue giá trị mặc định
     * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
     */
    public int getInt(String name, int defaultValue){
        return Optional.ofNullable(request.getParameter(name)).map(Integer::parseInt).orElse(defaultValue);
    }

    /**
     * Đọc số thực giá trị của tham số
     * @param name tên tham số
     * @param defaultValue giá trị mặc định
     * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
     */
    public double getDouble(String name, double defaultValue){
        return Optional.ofNullable(request.getParameter(name)).map(Double::parseDouble).orElse(defaultValue);
    }

    /**
     * Đọc giá trị boolean của tham số
     * @param name tên tham số
     * @param defaultValue giá trị mặc định
     * @return giá trị tham số hoặc giá trị mặc định nếu không tồn tại
     */
    public boolean getBoolean(String name, boolean defaultValue){
        return Optional.ofNullable(request.getParameter(name)).map(Boolean::parseBoolean).orElse(defaultValue);
    }

    /**
     * Đọc giá trị thời gian của tham số
     * @param name tên tham số
     * @param pattern là định dạng thời gian
     * @return giá trị tham số hoặc null nếu không tồn tại
     * @throws RuntimeException lỗi sai định dạng
     */
    public Date getDate(String name, String pattern) {
        return Optional.ofNullable(request.getParameter(name)).map(value -> {
                try {
                    return new SimpleDateFormat(pattern).parse(value);
                } catch (ParseException e) {
                    throw new RuntimeException("Sai định dạng " + name, e);
                }
            }).orElse(null);
    }

    /**
     * Lưu file upload vào thư mục
     * @param file chứa file upload từ client
     * @param path đường dẫn tính từ webroot
     * @return đối tượng chứa file đã lưu hoặc null nếu không có file upload
     * @throws RuntimeException lỗi lưu file
     */
    public File save(MultipartFile file, String path) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) return null;

        try {
            File destination = new File(request.getServletContext().getRealPath(path), file.getOriginalFilename());
            destination.getParentFile().mkdirs();
            file.transferTo(destination);
            return destination;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi lưu file: " + file.getOriginalFilename(), e);
        }
    }
}