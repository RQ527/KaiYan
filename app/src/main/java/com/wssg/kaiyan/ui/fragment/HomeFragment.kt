package com.wssg.kaiyan.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.wssg.kaiyan.viewmodel.HomeFragmentViewModel
import com.wssg.kaiyan.R
import com.wssg.kaiyan.ui.activity.PlayVideoActivity
import com.wssg.lib.base.base.ui.mvvm.BaseVmFragment
import com.wssg.lib.base.bean.BannerBean
import com.wssg.lib.base.net.DataState
import com.wssg.lib.base.widget.MyBannerView

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:
 */
class HomeFragment : BaseVmFragment<HomeFragmentViewModel>() {
    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_home, null)
        val bannerView = view.findViewById<MyBannerView>(R.id.banner_fragHome)
        val data =
            mutableListOf(BannerBean("http://img.kaiyanapp.com/7ea328a893aa1f092b9328a53494a267.png?imageMogr2/quality/60/format/jpg","测试1"),
                BannerBean("http://img.kaiyanapp.com/cd1244c8c75295747ebde0521207e668.jpeg?imageMogr2/quality/60/format/jpg","测试2")
            )
        bannerView.submitData(data)
        bannerView.setOnItemClicked(object :MyBannerView.OnItemClicked{
            override fun onLicked(position: Int) {
                Toast.makeText(requireContext(),"$position",Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val intent = Intent(requireActivity(), PlayVideoActivity::class.java)
//        viewModel.bannersLiveData.observe(requireActivity()) {
//            Toast.makeText(requireContext(), "${it.dataState}", Toast.LENGTH_SHORT).show()
//            if (it.dataState == DataState.STATE_SUCCESS) {
//                val id =
//                    it.itemList?.get(0)?.data?.itemList?.get(0)?.data?.header?.id?.toString()
//            }
//        }
        intent.putExtra("videoId", "311878")


    }
}