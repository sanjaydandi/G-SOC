package in.ascentrasolutions.krishi.Helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class LinkOpen {
    private final Context context;

    public LinkOpen(Context context) {
        this.context = context;
    }

    public void OpenClick(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }
}