$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $PSScriptRoot
$source = Join-Path $root "src\main\java\me\M1ran\zones\api"
$target = Join-Path $PSScriptRoot "src\main\java\me\M1ran\zones\api"

if (Test-Path $target) {
    Remove-Item -LiteralPath $target -Recurse -Force
}

New-Item -ItemType Directory -Path $target -Force | Out-Null

Get-ChildItem -Path $source -Filter *.java | Where-Object {
    $_.Name -ne "DefaultZonesApi.java"
} | Copy-Item -Destination $target

Write-Host "Zones API sources exported to $target"
