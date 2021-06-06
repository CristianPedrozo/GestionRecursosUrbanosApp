
import com.example.recolectar_app.entities.Camion2
import java.util.ArrayList

class DeleteCamionRequest {
    var actionType : String = "delete"
    var entities = ArrayList<Camion2>()

    fun addEntitie(entitie: Camion2){
        entities.add(entitie)
    }
}