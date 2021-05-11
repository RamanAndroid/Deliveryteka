package com.example.deliveryteka.data

import androidx.paging.PagingSource
import com.example.deliveryteka.api.DeliverytekaApi
import com.example.deliveryteka.data.models.MedicineInfo
import retrofit2.HttpException
import java.io.IOException

private const val MEDICINE_STARTING_PAGE_INDEX = 0

class MedicinePagingSource(
    private val deliverytekaApi: DeliverytekaApi,
    private val query: String
) : PagingSource<Int, MedicineInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MedicineInfo> {
        val position = params.key ?: MEDICINE_STARTING_PAGE_INDEX



        return try {
            val response = deliverytekaApi.searchMedicine(query, position, params.loadSize)
            val medicines = response.result

            LoadResult.Page(
                data = medicines,
                prevKey = if (position == MEDICINE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (medicines.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

}