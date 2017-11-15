package tech.ericntd.githubsearch.repositories;

import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;
import tech.ericntd.githubsearch.models.SearchResponse;

/**
 * ======= DATA LAYER
 * <p>
 * Simplified version of MVP-Clean as described
 * <a href="https://github.com/android10/Android-CleanArchitecture">here</a> and
 * <a href="https://github.com/googlesamples/android-architecture/tree/todo-mvp-clean/">here</a>
 * (Merged Domain Layer into the Presenter)
 * -------
 * <p>
 * "Repository" in the class name here means "repository" layer in MVP-Clean architecture, don't
 * get confused with the repository objects we are retrieving here
 */
public class GitHubRepository {
    private final GitHubApi gitHubApi;

    public GitHubRepository(@NonNull final GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    public void searchRepos(@NonNull final String query, @NonNull final GitHubRepositoryCallback callback) {
        Call<SearchResponse> call = gitHubApi.searchRepos(query);
        call.enqueue(new retrofit2.Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call,
                                   @NonNull Response<SearchResponse> response) {
                callback.handleGitHubResponse(response);
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call,
                                  @NonNull Throwable t) {
                Log.e("", "onFailure", t);
                callback.handleGitHubError();
            }
        });
    }

    public interface GitHubRepositoryCallback {
        void handleGitHubResponse(Response<SearchResponse> response);

        void handleGitHubError();
    }
}
