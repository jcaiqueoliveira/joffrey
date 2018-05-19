package medsolution.medsolutionmed


import android.support.v7.widget.RecyclerView
import android.view.View

//class ListaPacientesAdapter(private val listaPacient: List<Pacient>) :
//                            Adapter<ViewHolder>() {
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val pacients = listaPacient[position]
//        holder?.let {
//            it.itemView.namePacient.text = pacients.name
//            it.itemView.leito.text = pacients.bed
//            it.itemView.image_paciente.letter = pacients.name
//            if(position.rem(2)==0){
//                it.itemView.image_paciente.shapeColor = it.itemView.context.resources.getColor(R.color.colorPrimary)
//            }else{
//                it.itemView.image_paciente.shapeColor = it.itemView.context.resources.getColor(android.R.color.black)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent?.context).inflate(R.layout.lista_pacientes_celula
//                , parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//       return listaPacient.size
//    }
//
//}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)