package com.ddm.boogle.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.ddm.boogle.R
import com.ddm.boogle.databinding.FragmentCategoryBinding
import com.ddm.boogle.model.api.BookItem
import com.ddm.boogle.view.favorite.CarouselAdapter
import com.ddm.boogle.viewmodel.category.AdventureCategoryViewModel
import com.ddm.boogle.viewmodel.category.AutoHelpCategoryViewModel
import com.ddm.boogle.viewmodel.category.BioCategoryViewModel
import com.ddm.boogle.viewmodel.category.FictionCategoryViewModel
import com.ddm.boogle.viewmodel.category.RomanceCategoryViewModel
import com.ddm.boogle.viewmodel.category.TerrorCategoryViewModel

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null

    private lateinit var terrorCategoryViewModel: TerrorCategoryViewModel
    private lateinit var viewPager1: ViewPager2
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager3: ViewPager2
    private lateinit var viewPager4: ViewPager2
    private lateinit var viewPager5: ViewPager2
    private lateinit var viewPager6: ViewPager2
    private lateinit var viewPager7: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_category, container, false)
        viewPager1 = root.findViewById(R.id.categoryViewPager_1)
        viewPager2 = root.findViewById(R.id.categoryViewPager_2)
        viewPager3 = root.findViewById(R.id.categoryViewPager_3)
        viewPager4 = root.findViewById(R.id.categoryViewPager_4)
        viewPager6 = root.findViewById(R.id.categoryViewPager_6)
        viewPager7 = root.findViewById(R.id.categoryViewPager_7)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category1ViewModel = ViewModelProvider(this).get(AdventureCategoryViewModel::class.java)
        category1ViewModel.searchBooksByAdventureCategory()
        category1ViewModel.searchResult.observe(viewLifecycleOwner) { books ->
            if (books.isNotEmpty()) {
                setupViewPagerAdapter(books, viewPager1)
            }
        }

        val category2ViewModel = ViewModelProvider(this).get(TerrorCategoryViewModel::class.java)
        category2ViewModel.searchBooksByTerrorCategory()
        category2ViewModel.searchResult.observe(viewLifecycleOwner) { books ->
            if (books.isNotEmpty()) {
                setupViewPagerAdapter(books, viewPager2)
            }
        }

        val category3ViewModel = ViewModelProvider(this).get(RomanceCategoryViewModel::class.java)
        category3ViewModel.searchBooksByRomanceCategory()
        category3ViewModel.searchResult.observe(viewLifecycleOwner) { books ->
            if (books.isNotEmpty()) {
                setupViewPagerAdapter(books, viewPager3)
            }
        }

        val category4ViewModel = ViewModelProvider(this).get(FictionCategoryViewModel::class.java)
        category4ViewModel.searchBooksByFictionCategory()
        category4ViewModel.searchResult.observe(viewLifecycleOwner) { books ->
            if (books.isNotEmpty()) {
                setupViewPagerAdapter(books, viewPager4)
            }
        }

        val category6ViewModel = ViewModelProvider(this).get(AutoHelpCategoryViewModel::class.java)
        category6ViewModel.searchBooksByAutoHelpCategory()
        category6ViewModel.searchResult.observe(viewLifecycleOwner) { books ->
            if (books.isNotEmpty()) {
                setupViewPagerAdapter(books, viewPager6)
            }
        }

        val category7ViewModel = ViewModelProvider(this).get(BioCategoryViewModel::class.java)
        category7ViewModel.searchBooksByBioCategory()
        category7ViewModel.searchResult.observe(viewLifecycleOwner) { books ->
            if (books.isNotEmpty()) {
                setupViewPagerAdapter(books, viewPager7)
            }
        }
    }

    private fun setupViewPagerAdapter(books: List<BookItem>, viewPager: ViewPager2) {
        viewPager.adapter = CarouselAdapter(books)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}