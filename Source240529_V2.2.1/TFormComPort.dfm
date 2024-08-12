object FormComPort: TFormComPort
  Left = 358
  Top = 155
  BorderStyle = bsNone
  Caption = 'Comm. Set Window'
  ClientHeight = 617
  ClientWidth = 315
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
    Width = 315
    Height = 617
    Align = alCustom
    Alignment = taLeftJustify
    BevelOuter = bvNone
    TabOrder = 0
    object pnMain: TTntPanel
      Left = 4
      Top = 4
      Width = 307
      Height = 610
      BevelOuter = bvNone
      Color = clBlack
      TabOrder = 0
      object laRTime: TTntLabel
        Left = 17
        Top = 575
        Width = 104
        Height = 20
        AutoSize = False
        Caption = 'Response Timeout'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
      end
      object TntLabel9: TTntLabel
        Left = 169
        Top = 575
        Width = 26
        Height = 15
        AutoSize = False
        Caption = 'Sec'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentFont = False
      end
      object pnCaption: TTntPanel
        Left = 0
        Top = 0
        Width = 307
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
        TabOrder = 0
        OnMouseDown = pnCaptionMouseDown
        OnMouseMove = pnCaptionMouseMove
        OnMouseUp = pnCaptionMouseUp
        object pnBorderIcon: TTntPanel
          Left = 231
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
      object pnBLEDSoftNet: TTntPanel
        Left = 17
        Top = 523
        Width = 90
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 4
        object BLEDSoftNet: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 90
          Height = 30
          Caption = 'DABITNet'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clBlack
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = BLEDSoftNetClick
        end
      end
      object pnBPortCon: TTntPanel
        Left = 17
        Top = 479
        Width = 105
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 5
        object BPortCon: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 105
          Height = 30
          Caption = 'Open Port'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clBlack
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = BPortConClick
        end
      end
      object pnBPortClose: TTntPanel
        Left = 185
        Top = 479
        Width = 105
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 6
        object BPortClose: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 105
          Height = 30
          Caption = 'Close Port'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clBlack
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = BPortCloseClick
        end
      end
      object pnBitBtnOk: TTntPanel
        Left = 201
        Top = 567
        Width = 88
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 7
        object BitBtnOk: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 88
          Height = 30
          Caption = 'Close'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clBlack
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = BitBtnOkClick
        end
      end
      object gbSerial: TTntPanel
        Left = 16
        Top = 36
        Width = 273
        Height = 141
        BevelOuter = bvNone
        TabOrder = 9
        object pnSerial: TTntPanel
          Left = 2
          Top = 2
          Width = 269
          Height = 137
          BevelOuter = bvNone
          Color = clBlack
          TabOrder = 0
          object TeLabel1: TTeLabel
            Left = 13
            Top = 42
            Width = 121
            Height = 20
            AutoSize = False
            Caption = 'Baudrate   :'
            Color = clBtnFace
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentColor = False
            ParentFont = False
            HotTrack = False
          end
          object Label1: TTeLabel
            Left = 13
            Top = 14
            Width = 116
            Height = 16
            AutoSize = False
            Caption = 'COM Port :'
            Color = clBtnFace
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentColor = False
            ParentFont = False
            HotTrack = False
          end
          object laRS485: TTntLabel
            Left = 34
            Top = 68
            Width = 113
            Height = 19
            AutoSize = False
            Caption = 'RS-485 Address'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
            Layout = tlCenter
            OnClick = cbRS485Click
          end
          object ComBox1: TTntComboBox
            Left = 149
            Top = 12
            Width = 110
            Height = 21
            ImeName = 'Microsoft IME 2010'
            ItemHeight = 13
            TabOrder = 0
            OnClick = ComBox1ListBoxClick
            OnDropDown = ComBox1DropDown
          end
          object BaudBox1: TTntComboBox
            Left = 149
            Top = 40
            Width = 110
            Height = 21
            ImeName = 'Microsoft IME 2010'
            ItemHeight = 13
            TabOrder = 1
            Items.WideStrings = (
              '2400'
              '4800'
              '9600'
              '19200'
              '38400'
              '57600'
              '115200'
              '230400'
              '460800'
              '921600')
          end
          object pnBSystem: TTntPanel
            Left = 149
            Top = 97
            Width = 110
            Height = 30
            Align = alCustom
            Alignment = taLeftJustify
            BevelOuter = bvNone
            Color = clGray
            TabOrder = 2
            object BSystem: TTntSpeedButton
              Left = 0
              Top = 0
              Width = 110
              Height = 30
              Caption = 'Device Manager'
              Font.Charset = DEFAULT_CHARSET
              Font.Color = clBlack
              Font.Height = -11
              Font.Name = 'MS Sans Serif'
              Font.Style = []
              ParentFont = False
              OnClick = BSystemClick
            end
          end
          object pnBSearch: TTntPanel
            Left = 34
            Top = 97
            Width = 110
            Height = 30
            Align = alCustom
            Alignment = taLeftJustify
            BevelOuter = bvNone
            Color = clGray
            TabOrder = 3
            object BSearch: TTntSpeedButton
              Left = 0
              Top = 0
              Width = 110
              Height = 30
              Caption = 'Baudrate Search'
              Font.Charset = DEFAULT_CHARSET
              Font.Color = clBlack
              Font.Height = -11
              Font.Name = 'MS Sans Serif'
              Font.Style = []
              ParentFont = False
              OnClick = BSearchClick
            end
          end
          object cbRS485: TTntCheckBox
            Left = 16
            Top = 71
            Width = 13
            Height = 17
            TabOrder = 4
            OnClick = cbRS485Click
          end
          object cbDIBDAddress: TTntComboBox
            Left = 149
            Top = 68
            Width = 110
            Height = 21
            ImeName = 'Microsoft IME 2010'
            ItemHeight = 13
            TabOrder = 5
            Visible = False
            OnChange = cbDIBDAddressClick
            OnClick = cbDIBDAddressClick
            Items.WideStrings = (
              '0'
              '1'
              '2'
              '3'
              '4'
              '5'
              '6'
              '7'
              '8'
              '9'
              '10'
              '11'
              '12'
              '13'
              '14'
              '15')
          end
        end
      end
      object rbSerial: TTntRadioButton
        Left = 24
        Top = 29
        Width = 54
        Height = 17
        Caption = 'Serial'
        Color = clBlack
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentColor = False
        ParentFont = False
        TabOrder = 1
        OnClick = rbComKind
      end
      object gbLAN: TTntPanel
        Left = 17
        Top = 195
        Width = 273
        Height = 78
        BevelOuter = bvNone
        TabOrder = 10
        object pnLAN: TTntPanel
          Left = 2
          Top = 2
          Width = 269
          Height = 74
          BevelOuter = bvNone
          Color = clBlack
          TabOrder = 0
          object TntLabel4: TTntLabel
            Left = 13
            Top = 19
            Width = 65
            Height = 17
            AutoSize = False
            Caption = 'IP Address :'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
          end
          object Label3: TTntLabel
            Left = 13
            Top = 46
            Width = 73
            Height = 17
            AutoSize = False
            Caption = 'IP Port       :'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
          end
          object Edit2: TEdit
            Left = 88
            Top = 16
            Width = 170
            Height = 21
            ImeName = #54620#44397#50612' '#51077#47141' '#49884#49828#53596' (IME 2000)'
            TabOrder = 0
          end
          object Edit3: TEdit
            Left = 184
            Top = 44
            Width = 74
            Height = 21
            ImeName = #54620#44397#50612' '#51077#47141' '#49884#49828#53596' (IME 2000)'
            TabOrder = 1
          end
        end
      end
      object gbServer: TTntPanel
        Left = 17
        Top = 291
        Width = 273
        Height = 78
        BevelOuter = bvNone
        TabOrder = 11
        object pnServer: TTntPanel
          Left = 2
          Top = 2
          Width = 269
          Height = 74
          BevelOuter = bvNone
          Color = clBlack
          TabOrder = 0
          object TntLabel5: TTntLabel
            Left = 13
            Top = 22
            Width = 73
            Height = 17
            AutoSize = False
            Caption = 'IP Port       :'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
          end
          object TntLabel3: TTntLabel
            Left = 13
            Top = 47
            Width = 65
            Height = 17
            AutoSize = False
            Caption = 'IP Address :'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
          end
          object TntLabel6: TTntLabel
            Left = 72
            Top = 47
            Width = 185
            Height = 17
            Alignment = taRightJustify
            AutoSize = False
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWindowText
            Font.Height = -12
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
          end
          object Edit6: TEdit
            Left = 184
            Top = 20
            Width = 74
            Height = 21
            ImeName = #54620#44397#50612' '#51077#47141' '#49884#49828#53596' (IME 2000)'
            TabOrder = 0
          end
        end
      end
      object gbUDP: TTntPanel
        Left = 17
        Top = 387
        Width = 273
        Height = 78
        BevelOuter = bvNone
        TabOrder = 12
        object pnUDP: TTntPanel
          Left = 2
          Top = 2
          Width = 269
          Height = 74
          BevelOuter = bvNone
          Color = clBlack
          TabOrder = 0
          object TntLabel1: TTntLabel
            Left = 13
            Top = 19
            Width = 65
            Height = 17
            AutoSize = False
            Caption = 'IP Address :'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
          end
          object TntLabel2: TTntLabel
            Left = 13
            Top = 46
            Width = 73
            Height = 17
            AutoSize = False
            Caption = 'IP Port :'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clWhite
            Font.Height = -11
            Font.Name = 'MS Sans Serif'
            Font.Style = []
            ParentFont = False
            Transparent = True
          end
          object Edit4: TEdit
            Left = 88
            Top = 16
            Width = 169
            Height = 21
            ImeName = #54620#44397#50612' '#51077#47141' '#49884#49828#53596' (IME 2000)'
            TabOrder = 0
          end
          object Edit5: TEdit
            Left = 184
            Top = 44
            Width = 74
            Height = 21
            Enabled = False
            ImeName = #54620#44397#50612' '#51077#47141' '#49884#49828#53596' (IME 2000)'
            TabOrder = 1
          end
        end
      end
      object rbLAN: TTntRadioButton
        Left = 24
        Top = 188
        Width = 99
        Height = 17
        Caption = 'TCP/IP Client'
        Color = clBlack
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentColor = False
        ParentFont = False
        TabOrder = 2
        OnClick = rbComKind
      end
      object rbServer: TTntRadioButton
        Left = 24
        Top = 284
        Width = 103
        Height = 17
        Caption = 'TCP/IP Sever'
        Color = clBlack
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentColor = False
        ParentFont = False
        TabOrder = 3
        OnClick = rbComKind
      end
      object rbUDP: TTntRadioButton
        Left = 24
        Top = 380
        Width = 48
        Height = 17
        Caption = 'UDP'
        Color = clBlack
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWhite
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ParentColor = False
        ParentFont = False
        TabOrder = 8
        OnClick = rbComKind
      end
      object pnDivide: TTntPanel
        Left = 0
        Top = 25
        Width = 307
        Height = 2
        Align = alTop
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clBlack
        TabOrder = 13
        OnMouseDown = pnCaptionMouseDown
        OnMouseUp = pnCaptionMouseUp
      end
      object cbWaitTime: TTntComboBox
        Left = 124
        Top = 572
        Width = 43
        Height = 21
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clBlack
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        ImeName = 'Microsoft IME 2010'
        ItemHeight = 13
        ParentFont = False
        TabOrder = 14
        OnClick = cbWaitTimeClick
        Items.WideStrings = (
          '1'
          '2'
          '3'
          '4'
          '5'
          '6'
          '7'
          '8'
          '9'
          '10')
      end
      object pnBConnect: TTntPanel
        Left = 199
        Top = 523
        Width = 90
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 15
        object BConnect: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 90
          Height = 30
          Caption = 'Connect DABIT'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clBlack
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = BConnectClick
        end
      end
      object pnBBluetooth: TTntPanel
        Left = 108
        Top = 523
        Width = 90
        Height = 30
        Align = alCustom
        Alignment = taLeftJustify
        BevelOuter = bvNone
        Color = clGray
        TabOrder = 16
        object BBluetooth: TTntSpeedButton
          Left = 0
          Top = 0
          Width = 90
          Height = 30
          Caption = 'Bluetooth'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clBlack
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ParentFont = False
          OnClick = BBluetoothClick
        end
      end
    end
  end
end
