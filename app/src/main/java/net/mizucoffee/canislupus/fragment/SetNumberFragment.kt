package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_set_number.*
import net.mizucoffee.canislupus.activity.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.adapter.PositionAdapter
import net.mizucoffee.canislupus.viewmodel.SetNumberViewModel

class SetNumberFragment : Fragment() {

    companion object {
        fun newInstance() = SetNumberFragment()
    }

    private lateinit var vm: SetNumberViewModel
    private fun getGVM() = (activity as GameActivity).gameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_set_number, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProviders.of(this).get(SetNumberViewModel::class.java)

        positionRecycler.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = PositionAdapter(getGVM().getPlayers().size)
        }

        listenNextBtn(vm)
        observePositionList(vm)
        observeTransition(vm)
    }

    fun listenNextBtn(viewModel: SetNumberViewModel) {
        nextBtn.setOnClickListener {
            viewModel.initPosition(
                (positionRecycler.adapter as PositionAdapter).countList,
                getGVM().getPlayers()
            )
            viewModel.next()
        }
    }

    fun observePositionList(viewModel: SetNumberViewModel) {
        viewModel.positionList.observe(this, Observer {
            getGVM().setPositionList(it)
        })
    }

    fun observeTransition(viewModel: SetNumberViewModel) {
        viewModel.transitionLiveData.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, ConfirmPositionFragment.newInstance())
                ?.commit()
        })
    }
}