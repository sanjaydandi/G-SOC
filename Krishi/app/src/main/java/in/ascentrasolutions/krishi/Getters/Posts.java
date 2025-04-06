package in.ascentrasolutions.krishi.Getters;

public class Posts {

    private final String post_image, post_likes, post_title, post_profile_name, profile_id, post_id, profile_image, post_bio, post_time, comment_count;
    private final boolean isInteracted, isLiked;
    public String getPost_image() {
        return post_image;
    }
    public String getPost_likes() {
        return post_likes;
    }
    public String getPost_title() {
        return post_title;
    }
    public String getPost_profile_name() {
        return post_profile_name;
    }
    public String getProfile_id() {
        return profile_id;
    }
    public String getPost_id() {
        return post_id;
    }
    public String getProfile_image() {
        return profile_image;
    }

    public String getPost_bio() {
        return post_bio;
    }

    public String getPost_time() {
        return post_time;
    }

    public String getComment_count() {
        return comment_count;
    }

    public boolean isInteracted() {
        return isInteracted;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public Posts(String postImage, String postLikes, String post_title, String post_profile_name, String profile_id, String post_id, String profile_image, String post_bio, String post_time, String comment_count, boolean isInteracted, boolean isLiked) {

        post_image = postImage;
        post_likes = postLikes;
        this.post_title = post_title;
        this.post_profile_name = post_profile_name;
        this.profile_id = profile_id;
        this.post_id = post_id;
        this.profile_image = profile_image;
        this.post_bio = post_bio;
        this.comment_count = comment_count;
        this.post_time = post_time;
        this.isInteracted = isInteracted;
        this.isLiked = isLiked;
    }

}
