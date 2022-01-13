package play.gator.roomassignment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import play.gator.ViewModal;
import play.gator.roomassignment.Adapter.CourseRVAdapter;
import play.gator.roomassignment.Model.CourseModal;

public class MainActivity extends AppCompatActivity {

    private RecyclerView coursesRV;
    private ViewModal viewmodal;
    String name,capital,border,img,region, subregion,population, lang;
    private final static String URL = "https://restcountries.com/v3.1/region/Asia";
    JSONArray jsonMainNode;
    int listsize;
    CircularProgressBar circularProgressBar;
    String finalresult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coursesRV = findViewById(R.id.idRVCourses);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);
        new FetchDataTask().execute(URL);
        circularProgressBar = (CircularProgressBar)findViewById(R.id.CircularProgressbar);
        circularProgressBar.setVisibility(View.VISIBLE);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.white));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_200));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.activity_horizontal_margin));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.activity_vertical_margin));
        int animationDuration = 3000;
        circularProgressBar.setProgressWithAnimation(100, animationDuration);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewmodal.deleteAllCourses();
                Toast.makeText(getApplicationContext(), "Data Has Been Deleted.", Toast.LENGTH_SHORT).show();

            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        coursesRV.setLayoutManager(mLayoutManager);


        final CourseRVAdapter adapter = new CourseRVAdapter();

        coursesRV.setAdapter(adapter);

        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

        viewmodal.getAllCourses().observe(this, new Observer<List<CourseModal>>() {
            @Override
            public void onChanged(List<CourseModal> models) {

              listsize=models.size();
                adapter.submitList(models);
            }
        });


    }
    private class FetchDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream = null;
            String result= null;
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);

            try {

                HttpResponse response = client.execute(httpGet);
                inputStream = response.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null){
                    result = convertInputStreamToString(inputStream);

                }
                else
                    result = "Failed to fetch data";

                return result;

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String dataFetched) {
            parseJSON(dataFetched);
        }


        private String convertInputStreamToString(InputStream inputStream) throws IOException{
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        private void parseJSON(String data){

            try{
                JSONArray jsonMainNode = new JSONArray(data);

                int jsonArrLength = jsonMainNode.length();
                for(int i=0; i < jsonArrLength-1; i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    JSONObject param = jsonMainNode.getJSONObject(i).getJSONObject("name");
                     name = param.getString("common");
                     capital = jsonChildNode.getJSONArray("capital").toString();
                     border = jsonChildNode.getJSONArray("borders").toString();
                    border = border.replace("[", "");
                    border = border.replace("]", "");
                    capital = capital.replace("[", "");
                    capital = capital.replace("]", "");
                    JSONObject flag = jsonChildNode.getJSONObject("flags");
                     img = flag.getString("png");
                     region=jsonChildNode.getString("region");
                     subregion=jsonChildNode.getString("subregion");
                     population=jsonChildNode.getString("population");
                    JSONObject language = jsonChildNode.getJSONObject("languages");
                     lang=language.toString();
                    lang = lang.replace("{", "");
                    lang = lang.replace("}", "");

                   if(listsize==0){
                        circularProgressBar.setVisibility(View.GONE);
                    CourseModal model = new CourseModal(name,capital,img,region,subregion,population,border,lang);
                    viewmodal.insert(model);

                      }
                   else{
                       circularProgressBar.setVisibility(View.GONE);
                   }

                }


            }catch(Exception e){
                Log.i("App", "Error parsing data" +e.getMessage());

            }
        }
    }

}
