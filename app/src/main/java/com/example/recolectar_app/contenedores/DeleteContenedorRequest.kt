
import com.example.recolectar_app.model.contenedor.ContenedorModel
import java.util.ArrayList

class DeleteContenedorRequest {
    var actionType : String = "delete"
    var entities = ArrayList<ContenedorModel>()

    fun addEntitie(entitie: ContenedorModel){
        entities.add(entitie)
    }
}