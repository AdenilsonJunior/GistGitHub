package br.com.adenilson.gist.presentation.favorite.adapter

//class FavoriteGistsAdapter : PagingDataAdapter<Gist, AbstractViewHolder<Gist>>(DIFF_UTIL) {
//
//    private val factory = FavoriteGistsFactory()
//
//    override fun onBindViewHolder(holder: AbstractViewHolder<Gist>, position: Int) {
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<Gist> {
//
//    }
//
//
//    private class FavoriteGistsFactory : ViewTypesFactory<Gist> {
//
//        override fun type(model: Gist): Int {
//            return R.layout.item_favorite_gist
//        }
//
//        override fun holder(
//            type: Int,
//            view: View,
//            listener: ViewTypesListener<Gist>
//        ): AbstractViewHolder<*> {
//            return FavoriteGistViewHolder(view, listener)
//        }
//
//    }
//
//    companion object {
//        val DIFF_UTIL = object : DiffUtil.ItemCallback<Gist>() {
//            override fun areItemsTheSame(oldItem: Gist, newItem: Gist): Boolean {
//                return oldItem.webId == newItem.webId && oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: Gist, newItem: Gist): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//}