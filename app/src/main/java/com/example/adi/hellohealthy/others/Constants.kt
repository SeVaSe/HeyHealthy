package com.example.adi.hellohealthy.others

import com.example.adi.hellohealthy.R

object Constants {


    /**
     Постоянные строки для вызова разных списков упражнений.
     **/
    const val EXERCISE_LIST_NAME = "exercise_list"
    const val MISCELLANEOUS_LIST = "Комплекс"
    const val CHEST_LIST = "Грудь"
    const val BACK_LIST = "Спина"
    const val BICEP_LIST = "Бицепс и руки"
    const val TRICEP_AND_ABS_LIST = "Трицепс и пресс"
    const val SHOULDER_LIST = "Плечи"
    const val LEGS_LIST = "Ноги"



    /**
    Все упражнения доступны в приложении.
     **/
    private val lunges = Exercise(0, "Выпад", R.drawable.ic_lunges, isCompleted = false, isSelected = false)
    private val pushUps = Exercise(1, "Отжимания", R.drawable.ic_push_ups, isCompleted = false, isSelected = false)
    private val bodyWeightSquats = Exercise(2, "Приседания с собственным весом", R.drawable.ic_squats, isCompleted = false, isSelected = false)
    private val overHeadDumbbellPress = Exercise(3, "Жим гантелей над головой", R.drawable.ic_overhead_dumbbelll_press, isCompleted = false, isSelected = false)
    private val dumbbellRows = Exercise(4, "Тяги гантелей", R.drawable.ic_dumbbell_rows, isCompleted = false, isSelected = false)
    private val romanianDeadLift = Exercise(5, "Румынская становая тяга", R.drawable.ic_romanian_deadlift, isCompleted = false, isSelected = false)
    private val burpees = Exercise(6, "Берпи", R.drawable.ic_burpees, isCompleted = false, isSelected = false)
    private val sidePlanks = Exercise(7, "Боковые планки", R.drawable.ic_side_planks, isCompleted = false, isSelected = false)
    private val planks = Exercise(8, "Планка", R.drawable.ic_planks, isCompleted = false, isSelected = false)
    private val gluteBridge = Exercise(9, "Ягодичный мостик", R.drawable.ic_glute_bridge, isCompleted = false, isSelected = false)
    private val halfPushUpHover = Exercise(10, "Полуотжимание при наведении", R.drawable.ic_half_push_up_hover, false, isSelected = false)
    private val jumpingJack = Exercise(11, "Прыжки с взмахом", R.drawable.ic_jumping_jack, isCompleted = false, isSelected = false)
    private val forearmPlank = Exercise(12,"Планка на предплечьях", R.drawable.ic_forearm_plank, isCompleted = false, isSelected = false)
    private val pullUp = Exercise(13, "Подтягивания", R.drawable.ic_pull_up, isCompleted = false, isSelected = false)
    private val hollowBodyHold = Exercise(14, "Удержание тела", R.drawable.ic_hollow_body_hold, isCompleted = false, isSelected = false)
    private val stepUps = Exercise(15, "Шаги вверх", R.drawable.ic_step_ups, isCompleted = false, isSelected = false)
    private val tricepDips = Exercise(16, "Отжимания на трицепс", R.drawable.ic_dips, isCompleted = false, isSelected = false)
    private val squatsJump = Exercise(17, "Приседания с прыжками", R.drawable.ic_squats_jumps, isCompleted = false, isSelected = false)
    private val sideLegRaises = Exercise(18, "Боковые подъемы ног", R.drawable.ic_side_leg_raises, isCompleted = false, isSelected = false)
    private val warriorBalance = Exercise(19, "Баланс воина", R.drawable.ic_warrior_balance, isCompleted = false, isSelected = false)
    private val goodMorning = Exercise(20, "Доброе утро", R.drawable.ic_good_morning, isCompleted = false, isSelected = false)
    private val hipThrust = Exercise(21, "Тяга бедра", R.drawable.ic_hip_thrust, isCompleted = false, isSelected = false)
    private val skaters = Exercise(22, "Конькобежец", R.drawable.ic_skaters, isCompleted = false, isSelected = false)
    private val calfRaises = Exercise(23, "Подъемы на носки", R.drawable.ic_calf_raises, isCompleted = false, isSelected = false)
    private val dumbBellPullover = Exercise(24, "Пуловер с гантелями", R.drawable.ic_dumbell_pullover, isCompleted = false, isSelected = false)
    private val chestDips = Exercise(25, "Отжимания на грудь", R.drawable.ic_dips_chest, isCompleted = false, isSelected = false)
    private val chestPress = Exercise(26, "Жим от груди", R.drawable.ic_chest_press, isCompleted = false, isSelected = false)
    private val twoArmDumbbellRow = Exercise(27, "Тяга гантели двумя руками", R.drawable.ic_two_arm_dumbbell_row, isCompleted = false, isSelected = false)
    private val deltoidRaises = Exercise(28, "Дельтовидные подъемы", R.drawable.ic_deltoid_raises, isCompleted = false, isSelected = false)
    private val dumbbellChestFly = Exercise(29, "Разведение гантелей на груди", R.drawable.ic_dumbbell_chest_fly, isCompleted = false, isSelected = false)
    private val inclinedPushUps = Exercise(30, "Наклонные отжимания", R.drawable.ic_inclined_pushups, isCompleted = false, isSelected = false)
    private val t_raises = Exercise(31, "T подъем", R.drawable.ic_t_raises, isCompleted = false, isSelected = false)
    private val singleArmDumbbellRows = Exercise(32, "Тяга гантелей на одной руке", R.drawable.ic_single_arm_dumbbell_rows, isCompleted = false, isSelected = false)
    private val deltRaises = Exercise(33, "Подъем дельт", R.drawable.ic_delt_raises, isCompleted = false, isSelected = false)
    private val plankWithLateralArmRaises = Exercise(34, "Планка с подъемом рук в стороны", R.drawable.ic_plank_with_lateral_arm_raises, isCompleted = false, isSelected = false)
    private val pushUpHold = Exercise(35, "Удержание отжиманий", R.drawable.ic_push_up_hold, isCompleted = false, isSelected = false)
    private val backAndBootyBlasters = Exercise(36, "Бластеры для спины и попки", R.drawable.ic_back_and_booty_blasters, isCompleted = false, isSelected = false)
    private val twister = Exercise(37, "Смерч", R.drawable.ic_twister, isCompleted = false, isSelected = false)
    private val pilatesPress = Exercise(38, "Пилатес пресс", R.drawable.ic_pilates_press, isCompleted = false, isSelected = false)
    private val invertedRows = Exercise(39, "Перевернутые строки", R.drawable.ic_inverted_rows, isCompleted = false, isSelected = false)
    private val bandPullApart = Exercise(40, "Группа раздвигается", R.drawable.ic_band_pull_apart, isCompleted = false, isSelected = false)
    private val chinUp = Exercise(41, "Выше нос!", R.drawable.ic_chin_up, isCompleted = false, isSelected = false)
    private val wideLiftedBicepCurl = Exercise(42, "Широкий подъем на бицепс", R.drawable.ic_wide_lifted_bicep_curl, isSelected = false, isCompleted = false)
    private val bicepsCurl = Exercise(43, "Сгибание бицепса", R.drawable.ic_bicep_curl, isCompleted = false, isSelected = false)
    private val plankShoulderTap = Exercise(44, "Планка плеч tap", R.drawable.ic_plank_shoulder_tap, isCompleted = false, isSelected = false)
    private val seatedRowWithResistanceBand = Exercise(45, "Сидячий ряд с полосой сопротивления", R.drawable.ic_seated_row_with_resistance_band, isCompleted = false, isSelected = false)
    private val standingBicepStretch = Exercise(46, "Растяжка бицепса стоя", R.drawable.ic_standing_bicep_stretch, isCompleted = false, isSelected = false)
    private val seatedBicepStretch = Exercise(47, "Растяжка бицепса сидя", R.drawable.ic_seated_bicep_stretch, isCompleted = false, isSelected = false)
    private val inchworm = Exercise(48, "Дюймовый червь", R.drawable.ic_inchworm, isCompleted = false, isSelected = false)
    private val zottmanCurl = Exercise(49, "Локон Зоттмана", R.drawable.ic_zottman_curl, isCompleted = false, isSelected = false)
    private val sidePlankWithArmExtension = Exercise(50, "Боковая планка с разгибанием рук", R.drawable.ic_side_plank_with_arm_extension, isCompleted = false, isSelected = false)
    private val dumbbellLyingTricepExtension = Exercise(51, "Разгибание гантели на трицепс лежа", R.drawable.ic_dumbbell_lying_tricep_extension, isCompleted = false, isSelected = false)
    private val crunch = Exercise(52, "Пресс", R.drawable.ic_crunch, isCompleted = false, isSelected = false)
    private val overheadTricepsExtensions = Exercise(53, "Разгибания трицепса над головой", R.drawable.ic_overhead_triceps_extensions, isCompleted = false, isSelected = false)
    private val bicycleCrunches = Exercise(54, "Велосипед хрустит", R.drawable.ic_bicycle_crunches, isCompleted = false, isSelected = false)
    private val ropePushDowns = Exercise(55, "Отжимания на веревке", R.drawable.ic_rope_push_downs, isCompleted = false, isSelected = false)
    private val supineDeadBug = Exercise(56, "Лежащий мертвый клоп", R.drawable.ic_supine_dead_bug, isCompleted = false, isSelected = false)
    private val lyingTricepsExtensions = Exercise(57, "Разгибания трицепса лежа", R.drawable.ic_lying_triceps_extensions, isCompleted = false, isSelected = false)
    private val hangingKneeRaise = Exercise(58, "Поднятие коленей в висе", R.drawable.ic_hanging_knee_raise, isCompleted = false, isSelected = false)
    private val dumbbellLateralRaise = Exercise(59, "Боковой подъем гантели", R.drawable.ic_dumbbell_lateral_raise, isCompleted = false, isSelected = false)
    private val militaryPress = Exercise(60, "Военная пресса", R.drawable.ic_military_press, isCompleted = false, isSelected = false)
    private val reverseFly = Exercise(61, "Обратный полет", R.drawable.ic_reverse_fly, isCompleted = false, isSelected = false)
    private val arnoldPress = Exercise(62, "Арнольд пресс", R.drawable.ic_arnold_press, isCompleted = false, isSelected = false)
    private val frontDeltoidRaise = Exercise(63, "Подъем передних дельт", R.drawable.ic_front_deltoid_raise, isCompleted = false, isSelected = false)
    private val divingDolphin = Exercise(64, "Дайвинг дельфин", R.drawable.ic_diving_dolphin, isCompleted = false, isSelected = false)
    private val cleanSquatPress = Exercise(65, "Чистый пресс для приседаний", R.drawable.ic_clean_squat_press, isCompleted = false, isSelected = false)
    private val scarecrowPress = Exercise(66, "Пугало пресс", R.drawable.ic_scarecrow_press, isCompleted = false, isSelected = false)



    /**
    Массив всех упражнений, доступных в приложении.

    name = ALL_EXERCISE_LIST
     **/
    private val ALL_EXERCISE_LIST = arrayListOf(
        lunges, pushUps, bodyWeightSquats, overHeadDumbbellPress, dumbbellRows, romanianDeadLift, burpees, sidePlanks, planks, gluteBridge, halfPushUpHover, jumpingJack, forearmPlank, pullUp, hollowBodyHold, stepUps, tricepDips, squatsJump, sideLegRaises, warriorBalance, goodMorning, hipThrust, skaters, calfRaises, dumbBellPullover, chestDips, chestPress, twoArmDumbbellRow, deltoidRaises, dumbbellChestFly, inclinedPushUps, t_raises, singleArmDumbbellRows, deltRaises, plankWithLateralArmRaises, pushUpHold, backAndBootyBlasters, twister, pilatesPress, invertedRows, bandPullApart, chinUp, wideLiftedBicepCurl, bicepsCurl, plankShoulderTap, seatedRowWithResistanceBand, standingBicepStretch, seatedBicepStretch, inchworm, zottmanCurl, sidePlankWithArmExtension, dumbbellLyingTricepExtension, crunch, overheadTricepsExtensions, bicycleCrunches, ropePushDowns, supineDeadBug, lyingTricepsExtensions, hangingKneeRaise, dumbbellLateralRaise, militaryPress, reverseFly, arnoldPress, frontDeltoidRaise, divingDolphin, cleanSquatPress, scarecrowPress
    )



    /**
    Функция получения массива всех упражнений, доступных в приложении.

    name = getAllExercisionList()

    return type = ArrayList<Exercise>
     **/
    fun getAllExerciseList(): ArrayList<Exercise> {
        return ALL_EXERCISE_LIST
    }



    /**
    Функция для получения различных массивов упражнений с использованием строки, например («1,2,4,5,7,9»). Здесь числа в строке указывают идентификаторы упражнений, которые необходимо передать в массив.

    name = getExerciseListForDisplayingOnHistoryDialog(exerciseArrStr: String)

    return type = ArrayList<Exercise>
     **/
    fun getExerciseListForDisplayingOnHistoryDialog(exerciseArrStr: String): ArrayList<Exercise> {
        val ans = ArrayList<Exercise>()
        val arrExerciseIdsStr = exerciseArrStr.split(",")
        for (i in arrExerciseIdsStr) {
            ans.add(ALL_EXERCISE_LIST[i.toInt()])
        }
        return ans
    }
}