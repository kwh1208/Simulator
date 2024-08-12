object FormLEDSet: TFormLEDSet
  Left = 485
  Top = 240
  BorderStyle = bsNone
  Caption = 'FormLEDSet'
  ClientHeight = 522
  ClientWidth = 523
  Color = clBlack
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object pnMainDivide: TTntPanel
    Left = 0
    Top = 0
    Width = 523
    Height = 522
    Align = alCustom
    Alignment = taLeftJustify
    BevelOuter = bvNone
    TabOrder = 0
    object pnMain: TTntPanel
      Left = 4
      Top = 4
      Width = 515
      Height = 514
      BevelOuter = bvNone
      Color = clBlack
      TabOrder = 0
      object laDispType: TTntLabel
        Left = 16
        Top = 34
        Width = 185
        Height = 17
        AutoSize = False
        Caption = 'Display Type :'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
      end
      object TntLabel2: TTntLabel
        Left = 209
        Top = 181
        Width = 89
        Height = 17
        AutoSize = False
        Caption = 'Color Order :'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
      end
      object Label6: TTntLabel
        Left = 209
        Top = 241
        Width = 89
        Height = 17
        AutoSize = False
        Caption = #49324#50857#51088' '#49444#47749'   :'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Caption_UTF7 = '+wKzGqceQ +wSS6hQ   :'
      end
      object laNotice: TTntLabel
        Left = 209
        Top = 34
        Width = 89
        Height = 17
        AutoSize = False
        Caption = 'Notice :'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
      end
      object TntLabel1: TTntLabel
        Left = 209
        Top = 208
        Width = 89
        Height = 17
        AutoSize = False
        Caption = 'Scan Order :'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
      end
      object TntLabel6: TTntLabel
        Left = 385
        Top = 388
        Width = 14
        Height = 19
        AutoSize = False
        Caption = 's'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -12
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        Transparent = True
        Layout = tlCenter
      end
      object lbDispType: TTntListBox
        Left = 16
        Top = 52
        Width = 185
        Height = 357
        Style = lbOwnerDrawFixed
        ImeName = 'Microsoft IME 2010'
        ItemHeight = 13
        TabOrder = 0
        OnClick = lbDispTypeClick
        OnDblClick = lbDispTypeDblClick
        OnDrawItem = lbDispTypeDrawItem
        OnKeyUp = lbDispTypeKeyUp
      end
      object eUserNotice: TTntMemo
        Left = 208
        Top = 260
        Width = 288
        Height = 73
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -15
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        MaxLength = 200
        ParentFont = False
        TabOrder = 1
      end
      object eNotice: TTntMemo
        Left = 208
        Top = 52
        Width = 288
        Height = 121
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -15
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        MaxLength = 200
        ParentFont = False
        ReadOnly = True
        TabOrder = 2
      end
      object pnCaption: TTntPanel
        Left = 0
        Top = 0
        Width = 515
        Height = 25
        Align = alTop
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clBlack
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = [fsBold]
        ParentFont = False
        TabOrder = 3
        OnMouseDown = pnCaptionMouseDown
        OnMouseMove = pnCaptionMouseMove
        OnMouseUp = pnCaptionMouseUp
        object pnBorderIcon: TTntPanel
          Left = 439
          Top = 0
          Width = 76
          Height = 25
          Align = alRight
          BevelOuter = bvNone
          Color = clBlack
          TabOrder = 0
          object btnHide: TTntSpeedButton
            Left = 0
            Top = 0
            Width = 25
            Height = 25
            Flat = True
            Glyph.Data = {
              76060000424D7606000000000000360400002800000018000000180000000100
              0800000000004002000000000000000000000001000000000000000000008E8E
              8E00808080008D8C8C007E7D7D008E8E8E006767670067676700000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000002020202020202020202
              0202020202020200000000000000010101010101010101010101010101010100
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000}
            Visible = False
            OnClick = btnHideClick
          end
          object btnMax: TTntSpeedButton
            Left = 25
            Top = 0
            Width = 25
            Height = 25
            Flat = True
            Glyph.Data = {
              76060000424D7606000000000000360400002800000018000000180000000100
              0800000000004002000000000000000000000001000000000000000000008080
              8000696868008D8C8C007E7D7D008E8E8E006767670067676700000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000101
              0101010101010101010101010101000000000000000001010101010101010101
              0101010101010000000000000000010100000000000000000000000001010000
              0000000000000101000000000000000000000000010100000000000000000101
              0000000000000000000000000101000000000000000001010000000000000000
              0000000001010000000000000000010100000000000000000000000001010000
              0000000000000101000000000000000000000000010100000000000000000101
              0000000000000000000000000101000000000000000001010000000000000000
              0000000001010000000000000000010100000000000000000000000001010000
              0000000000000101000000000000000000000000010100000000000000000101
              0000000000000000000000000101000000000000000001010000000000000000
              0000000001010000000000000000010101010101010101010101010101010000
              0000000000000101010101010101010101010101010100000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000}
            Visible = False
            OnClick = btnMaxClick
          end
          object btnExit: TTntSpeedButton
            Left = 50
            Top = 0
            Width = 25
            Height = 25
            Flat = True
            Glyph.Data = {
              76060000424D7606000000000000360400002800000018000000180000000100
              0800000000004002000000000000000000000001000000000000000000008685
              8500696868008D8C8C007E7D7D008E8E8E006767670067676700000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000102
              0000000000000000000000020100000000000000000603030200000000000000
              0000020303060000000000000000020303020000000000000002030302000000
              0000000000000002030302000000000002030302000000000000000000000000
              0203030200000002030302000000000000000000000000000002030302000203
              0302000000000000000000000000000000000203030403030200000000000000
              0000000000000000000000020305030200000000000000000000000000000000
              0000000203050302000000000000000000000000000000000000020303040303
              0200000000000000000000000000000000020303020002030302000000000000
              0000000000000000020303020000000203030200000000000000000000000002
              0303020000000000020303020000000000000000000002030302000000000000
              0002030302000000000000000002030302000000000000000000020303020000
              0000000000000102000000000000000000000002010000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000000000000000
              0000000000000000000000000000000000000000000000000000}
            OnClick = btnExitClick
          end
        end
      end
      object cbDispColor: TTntComboBox
        Left = 408
        Top = 177
        Width = 88
        Height = 21
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ImeName = 'Microsoft IME 2010'
        ItemHeight = 13
        ParentFont = False
        TabOrder = 4
        Items.WideStrings = (
          'RGB'
          'RBG'
          'GRB'
          'GBR'
          'BRG'
          'BGR'
          'NC1')
      end
      object cbScanOder: TTntComboBox
        Left = 408
        Top = 204
        Width = 88
        Height = 21
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ImeName = 'Microsoft IME 2010'
        ItemHeight = 13
        ParentFont = False
        TabOrder = 5
        Items.WideStrings = (
          '138 IC'
          '595 IC')
      end
      object pnBSave: TTntPanel
        Left = 317
        Top = 342
        Width = 88
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 6
        object BSave: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 88
          Height = 30
          Caption = #51200#51109
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWhite
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = BSaveClick
          Caption_UTF7 = '+yADHpQ'
        end
      end
      object pnDataSend: TTntPanel
        Left = 409
        Top = 342
        Width = 88
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 7
        object DataSend: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 88
          Height = 30
          Caption = #51204#49569
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWhite
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = DataSendClick
          Caption_UTF7 = '+yATBoQ'
        end
      end
      object pnBitBtnOk: TTntPanel
        Left = 409
        Top = 461
        Width = 88
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 8
        object BitBtnOk: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 88
          Height = 30
          Caption = #54869#51064'(&S)'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWhite
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = BitBtnOkClick
          Caption_UTF7 = '+1lXHeA(&S)'
        end
      end
      object pnDivide: TTntPanel
        Left = 0
        Top = 25
        Width = 515
        Height = 2
        Align = alTop
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clBlack
        TabOrder = 9
        OnMouseDown = pnCaptionMouseDown
        OnMouseUp = pnCaptionMouseUp
      end
      object gbDetail: TTntPanel
        Left = 16
        Top = 424
        Width = 371
        Height = 67
        BevelOuter = bvNone
        TabOrder = 10
        object pnDetail: TTntPanel
          Left = 2
          Top = 2
          Width = 367
          Height = 63
          BevelOuter = bvNone
          Color = clBlack
          TabOrder = 0
          object TntLabel3: TTntLabel
            Left = 65
            Top = 33
            Width = 22
            Height = 19
            AutoSize = False
            Caption = 'ms'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -12
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
            Layout = tlCenter
          end
          object TntLabel4: TTntLabel
            Left = 137
            Top = 33
            Width = 22
            Height = 19
            AutoSize = False
            Caption = 'ms'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -12
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
            Layout = tlCenter
          end
          object TntLabel5: TTntLabel
            Left = 17
            Top = 8
            Width = 56
            Height = 19
            Alignment = taCenter
            AutoSize = False
            Caption = 'Before'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -12
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
            Layout = tlCenter
          end
          object TntLabel7: TTntLabel
            Left = 89
            Top = 8
            Width = 56
            Height = 19
            Alignment = taCenter
            AutoSize = False
            Caption = 'After'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -12
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
            Layout = tlCenter
          end
          object pnBtnWrite: TTntPanel
            Left = 167
            Top = 22
            Width = 88
            Height = 30
            Align = alCustom
            Alignment = taLeftJustify
            BevelOuter = bvNone
            Color = clGray
            TabOrder = 0
            object BtnWrite: TTntSpeedButton
              Left = 0
              Top = 0
              Width = 88
              Height = 30
              Caption = #49444#51221
              Font.Charset = DEFAULT_CHARSET
              Font.Color = clWhite
              Font.Height = -11
              Font.Name = 'MS Sans Serif'
              Font.Style = []
              ParentFont = False
              OnClick = BtnWriteClick
              Caption_UTF7 = '+wSTIFQ'
            end
          end
          object pnBtnRead: TTntPanel
            Left = 263
            Top = 22
            Width = 88
            Height = 30
            Align = alCustom
            Alignment = taLeftJustify
            BevelOuter = bvNone
            Color = clGray
            TabOrder = 1
            object BtnRead: TTntSpeedButton
              Left = 0
              Top = 0
              Width = 88
              Height = 30
              Caption = #51069#44592
              Font.Charset = DEFAULT_CHARSET
              Font.Color = clWhite
              Font.Height = -11
              Font.Name = 'MS Sans Serif'
              Font.Style = []
              ParentFont = False
              OnClick = BtnWriteClick
              Caption_UTF7 = '+x32uMA'
            end
          end
          object eDetailDelayBefore: TTntEdit
            Left = 18
            Top = 29
            Width = 29
            Height = 23
            AutoSize = False
            BevelInner = bvNone
            BevelOuter = bvNone
            ImeName = 'Microsoft IME 2010'
            MaxLength = 2
            TabOrder = 2
            Text = '0'
          end
          object UpDownDetailDelayBefore: TTntUpDown
            Left = 47
            Top = 29
            Width = 16
            Height = 23
            Associate = eDetailDelayBefore
            Min = 0
            Max = 99
            Position = 0
            TabOrder = 3
            Wrap = False
          end
          object eDetailDelayAfter: TTntEdit
            Left = 90
            Top = 29
            Width = 29
            Height = 23
            AutoSize = False
            BevelInner = bvNone
            BevelOuter = bvNone
            ImeName = 'Microsoft IME 2010'
            MaxLength = 2
            TabOrder = 4
            Text = '0'
          end
          object UpDownDetailDelayAfter: TTntUpDown
            Left = 119
            Top = 29
            Width = 16
            Height = 23
            Associate = eDetailDelayAfter
            Min = 0
            Max = 99
            Position = 0
            TabOrder = 5
            Wrap = False
          end
        end
      end
      object laDetail: TTntPanel
        Left = 24
        Top = 415
        Width = 108
        Height = 20
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Caption = #48120#49464#51092#49345#49444#51221
        Color = clBlack
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
        TabOrder = 11
        Caption_UTF7 = '+u/jBOMeUwMHBJMgV'
      end
      object pnDataSendAuto: TTntPanel
        Left = 409
        Top = 382
        Width = 88
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 12
        object DataSendAuto: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 88
          Height = 30
          Caption = #51088#46041#51204#49569
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWhite
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = DataSendClick
          Caption_UTF7 = '+x5Cz2cgEwaE'
        end
      end
      object eDelay: TTntEdit
        Left = 338
        Top = 384
        Width = 29
        Height = 23
        AutoSize = False
        BevelInner = bvNone
        BevelOuter = bvNone
        ImeName = 'Microsoft IME 2010'
        MaxLength = 2
        TabOrder = 13
        Text = '3'
      end
      object UpDownDelay: TTntUpDown
        Left = 367
        Top = 384
        Width = 16
        Height = 23
        Associate = eDelay
        Min = 0
        Max = 99
        Position = 3
        TabOrder = 14
        Wrap = False
      end
    end
  end
end
