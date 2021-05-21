package com.wxj.entity;

public class User {
        private Long number;

        private String password;

        private String picture;

        private String realname;

        private String email;

        private Integer companyId;

        private Integer employeeId;

    public User(String realname, Integer employeeId,Long number,Integer companyId) {
        this.realname=realname;
        this.employeeId=employeeId;
        this.number=number;
        this.companyId=companyId;
    }

    public Long getNumber() {
            return number;
        }

        public void setNumber(Long number) {
            this.number = number;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password == null ? null : password.trim();
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture == null ? null : picture.trim();
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname == null ? null : realname.trim();
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email == null ? null : email.trim();
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public Integer getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Integer employeeId) {
            this.employeeId = employeeId;
        }
}
