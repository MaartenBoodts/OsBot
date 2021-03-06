package sudokuSolver.model;

public enum Tile {

    T1(10, 0, 0),
    T2(11, 0, 1),
    T3(12, 0, 2),
    T4(13, 0, 3),
    T5(14, 0, 4),
    T6(15, 0, 5),
    T7(16, 0, 6),
    T8(17, 0, 7),
    T9(18, 0, 8),
    T10(19, 1, 0),
    T11(20, 1, 1),
    T12(21, 1, 2),
    T13(22, 1, 3),
    T14(23, 1, 4),
    T15(24, 1, 5),
    T16(25, 1, 6),
    T17(26, 1, 7),
    T18(27, 1, 8),
    T19(28, 2, 0),
    T20(29, 2, 1),
    T21(30, 2, 2),
    T22(31, 2, 3),
    T23(32, 2, 4),
    T24(33, 2, 5),
    T25(34, 2, 6),
    T26(35, 2, 7),
    T27(36, 2, 8),
    T28(37, 3, 0),
    T29(38, 3, 1),
    T30(39, 3, 2),
    T31(40, 3, 3),
    T32(41, 3, 4),
    T33(42, 3, 5),
    T34(43, 3, 6),
    T35(44, 3, 7),
    T36(45, 3, 8),
    T37(46, 4, 0),
    T38(47, 4, 1),
    T39(48, 4, 2),
    T40(49, 4, 3),
    T41(50, 4, 4),
    T42(51, 4, 5),
    T43(52, 4, 6),
    T44(53, 4, 7),
    T45(54, 4, 8),
    T46(55, 5, 0),
    T47(56, 5, 1),
    T48(57, 5, 2),
    T49(58, 5, 3),
    T50(59, 5, 4),
    T51(60, 5, 5),
    T52(61, 5, 6),
    T53(62, 5, 7),
    T54(63, 5, 8),
    T55(64, 6, 0),
    T56(65, 6, 1),
    T57(66, 6, 2),
    T58(67, 6, 3),
    T59(68, 6, 4),
    T60(69, 6, 5),
    T61(70, 6, 6),
    T62(71, 6, 7),
    T63(72, 6, 8),
    T64(73, 7, 0),
    T65(74, 7, 1),
    T66(75, 7, 2),
    T67(76, 7, 3),
    T68(77, 7, 4),
    T69(78, 7, 5),
    T70(79, 7, 6),
    T71(80, 7, 7),
    T72(81, 7, 8),
    T73(82, 8, 0),
    T74(83, 8, 1),
    T75(84, 8, 2),
    T76(85, 8, 3),
    T77(86, 8, 4),
    T78(87, 8, 5),
    T79(88, 8, 6),
    T80(89, 8, 7),
    T81(90, 8, 8);

    int widgetChildId, Y, X;

    Tile(int widgetChildId, int y, int x) {
        this.widgetChildId = widgetChildId;
        Y = y;
        X = x;
    }

    public int getWidgetChildId() {
        return widgetChildId;
    }

    public int getY() {
        return Y;
    }

    public int getX() {
        return X;
    }
}