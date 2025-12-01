#!/bin/bash
set -e

# config
APP_NAME="commitic"
DEB_ARCH="amd64"
GRADLE_FILE="composeApp/build.gradle.kts"
CUSTOM_RESOURCES_DIR="composeApp/packaging/linux"
ICON_NAME="commitic.png" 
ICON_INSTALL_DIR="/usr/share/icons/hicolor/512x512/apps"
ORIGINAL_DEB_DIR="composeApp/build/compose/binaries/main/deb"
FINAL_DEB_DIR="composeApp/build/dist"



# versão
echo "### 1. Lendo versão do '$GRADLE_FILE'..."
if [ ! -f "$GRADLE_FILE" ]; then
    echo "ERRO: Arquivo Gradle não encontrado."
    exit 1
fi
VERSION=$(grep 'packageVersion =' "$GRADLE_FILE" | cut -d '"' -f 2)
if [ -z "$VERSION" ]; then
    echo "ERRO: Versão não encontrada."
    exit 1
fi
echo "  - Versão: $VERSION"


# buildar deb original
echo "### 2. Executando Gradle packageDeb..."
if [ ! -x "./gradlew" ]; then chmod +x ./gradlew; fi
./gradlew packageDeb --rerun-tasks


# desempacotar deb original
ORIGINAL_DEB_PATH=$(find "$ORIGINAL_DEB_DIR" -name "*.deb")
if [ -z "$ORIGINAL_DEB_PATH" ]; then
    echo "ERRO: DEB original não encontrado."
    exit 1
fi

WORK_DIR="composeApp/build/deb_work"
echo "### 3. Desempacotando em '$WORK_DIR'..."
rm -rf "$WORK_DIR"
mkdir -p "$WORK_DIR"
dpkg-deb -R "$ORIGINAL_DEB_PATH" "$WORK_DIR"


# modificando
echo "### 4. Aplicando correções..."
find "$WORK_DIR" -name "*.desktop" -type f -delete
if [ -f "$WORK_DIR/DEBIAN/postinst" ]; then
    sed -i '/xdg-desktop-menu/d' "$WORK_DIR/DEBIAN/postinst"
    echo "  - Linhas de erro removidas do script 'postinst'."
fi
if [ -f "$WORK_DIR/DEBIAN/prerm" ]; then
    sed -i '/xdg-desktop-menu/d' "$WORK_DIR/DEBIAN/prerm"
    echo "  - Linhas de erro removidas do script 'prerm'."
fi

# adicionar novo .desktop
DESTINATION_DESKTOP_DIR="$WORK_DIR/usr/share/applications"
mkdir -p "$DESTINATION_DESKTOP_DIR" 
DESKTOP_TEMPLATE="$CUSTOM_RESOURCES_DIR/$APP_NAME.desktop"

if [ -f "$DESKTOP_TEMPLATE" ]; then
    sed "s/@version@/$VERSION/g" "$DESKTOP_TEMPLATE" > "$DESTINATION_DESKTOP_DIR/$APP_NAME.desktop"
    echo "  - Novo .desktop injetado."
else
    echo "ERRO: Template .desktop não encontrado."
    exit 1
fi

# adicionar ícone 
ICON_SOURCE_PATH="$CUSTOM_RESOURCES_DIR/$ICON_NAME"
DESTINATION_ICON_DIR="$WORK_DIR/$ICON_INSTALL_DIR"
if [ -f "$ICON_SOURCE_PATH" ]; then
    mkdir -p "$DESTINATION_ICON_DIR"
    cp "$ICON_SOURCE_PATH" "$DESTINATION_ICON_DIR/"
    echo "  - Ícone injetado."
fi


# reempacotar 
FINAL_DEB_NAME="${APP_NAME}_${VERSION}_${DEB_ARCH}_custom.deb"
FINAL_DEB_PATH="$FINAL_DEB_DIR/$FINAL_DEB_NAME"
mkdir -p "$FINAL_DEB_DIR"
echo "### 5. Reempacotando..."

dpkg-deb --root-owner-group -b "$WORK_DIR" "$FINAL_DEB_PATH"

echo ""
echo "================================================="
echo "SUCESSO! Novo pacote gerado:"
echo "$FINAL_DEB_PATH"
echo "================================================="
