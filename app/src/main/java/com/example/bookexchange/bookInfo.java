package com.example.bookexchange;

    public class bookInfo {

        private String imageURL;
        private String bookName, authorName, genreName, isbn, userNameHome;

        public bookInfo () {

        }

        public bookInfo(String imageURL, String bookName, String authorName, String genreName, String isbn, String userNameHome) {
            this.imageURL = imageURL;
            this.bookName = bookName;
            this.authorName = authorName;
            this.genreName = genreName;
            this.isbn = isbn;
            this.userNameHome = userNameHome;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getGenreName() {
            return genreName;
        }

        public void setGenreName(String genreName) {
            this.genreName = genreName;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getUserNameHome() {
            return userNameHome;
        }

        public void setUserNameHome(String userNameHome) {
            this.userNameHome = userNameHome;
        }
    }
