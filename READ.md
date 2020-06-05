# GitHub Info Searcher
"GitHub Info Searcher" is an Android tool app that can help user to search info of GitHub by request GitHub API.

Right now this app provide the function to search GitHub user, and open the GitHub home web page of the user which was clicked in user list.

The project architecture is composed of the following components:

     * Android MVVM pattern using Koin library and Architecture Components (ViewModel and LiveData)
     * Rest API calls using Retrofit2 libray 
     * Android Paging library 

## How it works ?
Together, the components of the Paging Library organize a data flow from a background thread producer, and presentation on the UI thread.

That newly-created PagedList is sent to the PagedListAdapter on the UI thread. The PagedListAdapter then uses DiffUtil on a background thread to compute the difference between the current list and the new list. When the comparison is finished, the PagedListAdapter uses the list difference information to make appropriate call to RecyclerView.Adapter.notifyItemInserted() to signal that a new item was inserted.

The RecyclerView on the UI thread then knows that it only has to bind a single new item, and animate it appearing on screen.

## To Do List

     * Provide more search function about different type of info, for example: search GitHub Repository & Tag
     * Use local database and ORMapping libary to store data which was responsed by GitHub API, for example: SQLite, to avoid 403 rare limit error of GitHub API.
     * Unit Test with GitHub api request & UI
     * Fix GitHub Api 403 rate limit issue, which will cause this app can't receive data for a while 


