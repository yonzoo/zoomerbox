package com.zoomerbox.data.repository.impl

import com.zoomerbox.data.repository.ISeasonDropRepository
import com.zoomerbox.model.dto.BoxItemDTO
import com.zoomerbox.model.dto.CollectionDTO
import com.zoomerbox.model.dto.SeasonDropDTO
import com.zoomerbox.model.dto.ZoomerBoxDTO
import com.zoomerbox.model.enumeration.RarenessEnum

class MockSeasonDropRepository : ISeasonDropRepository {

    override fun getSeasonDrop(): SeasonDropDTO {
        return SeasonDropDTO(
            0,
            "Моксезон",
            "http://sun9-14.userapi.com/impg/c858020/v858020227/21d0a9/a0Zk6RVegvY" +
                    ".jpg?size=960x686&quality=96&sign=ec21464694ca9257d37c176ce1fd8418&type=album",
            getCollections()
        )
    }

    override fun getImplName(): String {
        return this::class.java.simpleName
    }

    private fun getCollections(): List<CollectionDTO> {
        val gearBoxCollection = CollectionDTO(
            "Гирбокс",
            getBoxes(listOf("X_GUCCI, X_BALENCIAGA", "X_ADIDAS", "X_NIKE"))
        )
        val geekBoxCollection = CollectionDTO(
            "Гикбокс",
            getBoxes(listOf("X_MARVEL, X_DC", "X_BUBBLECOMICS", "X_LUCASFILM"))
        )
        val snackBoxCollection = CollectionDTO(
            "Снекбокс",
            getBoxes(listOf("X_SNICKERS, X_CHEETOS", "X_ANIMECHOCOLATE", "X_KEKSNACK"))
        )
        val beautyBoxCollection = CollectionDTO(
            "Бьютибокс",
            getBoxes(listOf("X_CHANEL, X_MAYBELLINE", "X_L'OREAL", "X_BOBBIBROWN"))
        )
        return listOf(
            gearBoxCollection,
            geekBoxCollection,
            snackBoxCollection,
            beautyBoxCollection
        )
    }

    private fun getBoxes(names: List<String>): List<ZoomerBoxDTO> {
        val boxes = mutableListOf<ZoomerBoxDTO>()
        for (i in names.indices) {
            boxes.add(
                ZoomerBoxDTO(
                    name = names[i],
                    imageUrls = getMockBoxImageUrls(),
                    description = getMockBoxDescription(),
                    items = getItems(),
                    rareItems = getRareItems()
                )
            )
        }
        return boxes
    }

    private fun getItems(): List<BoxItemDTO> {
        val items = mutableListOf<BoxItemDTO>()
        for (i in 0..5) {
            items.add(
                BoxItemDTO(
                    name = "Предмет $i",
                    imageUrls = getMockItemImageUrls(),
                    description = getMockItemDescription(),
                    rareness = RarenessEnum.CASUAL
                )
            )
        }
        return items
    }

    private fun getRareItems(): List<BoxItemDTO> {
        val items = mutableListOf<BoxItemDTO>()
        for (i in 0..5) {
            items.add(
                BoxItemDTO(
                    name = "Редкий предмет $i",
                    imageUrls = getMockItemImageUrls(),
                    description = getMockItemDescription(),
                    rareness = if (i > 3) RarenessEnum.RARE else RarenessEnum.LEGENDARY
                )
            )
        }
        return items
    }

    private fun getMockItemImageUrls(): List<String> {
        return listOf(
            "https://www.originalstormtrooper.ru/image/cache/data/Hasbro/carbonized/Star%20Wars%20The%20Black%20Series%20Carbonised%20Collection%20Darth%20Vader-2-500x500.jpg",
            "https://images.ru.prom.st/791758768_w640_h640_krossovki-gucci-guchchi.jpg",
            "https://spkubani.club/files/9b6/9b60996ee78ecabb4f6a44de16ebd709.png"
        )
    }

    private fun getMockItemDescription(): String {
        return "Была ужасная пора,\n" +
                "Об ней свежо воспоминанье...\n" +
                "Об ней, друзья мои, для вас\n" +
                "Начну свое повествованье.\n" +
                "Печален будет мой рассказ."
    }


    private fun getMockBoxImageUrls(): List<String> {
        return listOf(
            "https://static.cdn.packhelp.com/wp-content/uploads/2019/06/06153013/plain-shipping-boxes-packhelp-kva.jpg",
            "https://img.freepik.com/free-psd/packaging-box-concept-mock-up_23-2148698796.jpg?size=626&ext=jpg&ga=GA1.2.1377421482.1624924800",
            "https://img.freepik.com/free-psd/packaging-box-mock-up_23-2148698794.jpg?size=626&ext=jpg&ga=GA1.1.1728028159.1615852800"
        )
    }

    private fun getMockBoxDescription(): String {
        return "Прошло сто лет, и юный град,\n" +
                "Полнощных стран краса и диво,\n" +
                "Из тьмы лесов, из топи блат\n" +
                "Вознесся пышно, горделиво;\n" +
                "Где прежде финский рыболов,\n" +
                "Печальный пасынок природы,\n" +
                "Один у низких берегов\n" +
                "Бросал в неведомые воды\n" +
                "Свой ветхой невод, ныне там\n" +
                "По оживленным берегам\n" +
                "Громады стройные теснятся\n" +
                "Дворцов и башен; корабли\n" +
                "Толпой со всех концов земли\n" +
                "К богатым пристаням стремятся;\n" +
                "В гранит оделася Нева;\n" +
                "Мосты повисли над водами;\n" +
                "Темно-зелеными садами\n" +
                "Ее покрылись острова,\n" +
                "И перед младшею столицей\n" +
                "Померкла старая Москва,"
    }
}
