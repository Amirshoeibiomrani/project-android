import com.example.myapplication.api.GetNotesResponse
import retrofit2.Response
import retrofit2.http.GET

interface NotesApiService {
    @GET("api/collections/notes/records")
    suspend fun getNotes(): Response<GetNotesResponse>
}