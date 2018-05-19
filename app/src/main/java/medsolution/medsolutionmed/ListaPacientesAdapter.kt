package medsolution.medsolutionmed


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lista_pacientes_celula.view.*
import medsolution.medsolutionmed.model.Pacient

class ListaPacientesAdapter(private val listaPacient: List<Pacient>,
                            private val context:Context) :
                            Adapter<ListaPacientesAdapter.ViewHolder>() {

    val pacients = ArrayList<Pacient>();

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val pacients = pacients[position]
        holder?.let {
            it.itemView.text_nomePaciente.text = pacients.name
            it.itemView.text_descricao.text = pacients.bed
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.lista_pacientes_celula
                , parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return pacients.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
