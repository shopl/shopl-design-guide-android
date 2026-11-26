// Tiptap transactions and list editing also run on WebViews predating these methods.
import 'core-js/es/array/at'
import 'core-js/es/array/find-last'
import { Editor, Extension } from '@tiptap/core'
import { Highlight } from '@tiptap/extension-highlight'
import { TextAlign } from '@tiptap/extension-text-align'
import { Placeholder } from '@tiptap/extensions'
import { finishLink, runAction, toolbarState } from './toolbar.js'
import StarterKit from '@tiptap/starter-kit'

const post = (type, fields = {}) => window.SDGRichTextHost.postMessage(JSON.stringify({ type, ...fields }))

function indentSize(value) {
  return /^\d+(\.\d+)?(px|em|rem)$/.test(value) ? value : null
}

const ParagraphIndent = Extension.create({
  name: 'paragraphIndent',
  addGlobalAttributes() {
    return [{
      types: ['paragraph', 'heading'],
      attributes: {
        indent: {
          default: null,
          parseHTML: (element) => indentSize(element.style.marginInlineStart || element.style.marginLeft),
          renderHTML: ({ indent }) => indentSize(indent) ? { style: `margin-inline-start: ${indent}` } : {},
        },
      },
    }]
  },
})

const caret = document.querySelector('#caret')
let editor
let placeholder = ''
let input = false
let linkSelection
let previousToolbar
let previousDoc
let previousState
let caretFrame = 0

function refreshCaret() {
  if (!editor) return
  cancelAnimationFrame(caretFrame)
  caretFrame = requestAnimationFrame(() => {
    const { selection } = editor.state
    const composing = editor.view.composing
    document.body.toggleAttribute('data-composing', composing)
    const visible = editor.isEditable && editor.isFocused && selection.empty && !composing
    caret.toggleAttribute('data-visible', visible)
    if (visible) {
      const position = editor.view.coordsAtPos(selection.head)
      const height = caret.offsetHeight
      const maxLeft = document.documentElement.clientWidth - caret.offsetWidth
      caret.style.left = `${Math.min(position.left + 2, maxLeft)}px`
      caret.style.top = `${position.top + (position.bottom - position.top - height) / 2}px`
    }
  })
}

function publishState(current) {
  if (input) {
    const doc = current.state.doc
    if (doc === previousDoc) return
    previousDoc = doc
    const text = doc.textBetween(0, doc.content.size, '\n', node => node.type.name === 'hardBreak' ? '\n' : '')
    post('content', { html: current.getHTML(), count: Array.from(text).length })
    return
  }
  const state = {
    html: current.getHTML(),
    plainText: current.getText({ blockSeparator: '\n' }),
    isEmpty: current.isEmpty,
    hasFocus: current.isFocused,
  }
  const serialized = JSON.stringify(state)
  if (serialized !== previousState) {
    previousState = serialized
    post('state', state)
  }
}

function refreshToolbar(current) {
  if (input) {
    const state = toolbarState(current)
    const serialized = JSON.stringify(state)
    if (serialized !== previousToolbar) {
      previousToolbar = serialized
      post('toolbar', state)
    }
  }
  refreshCaret()
}

function updateAppearance(current) {
  publishState(current)
  refreshToolbar(current)
}

function createEditor(html, enabled) {
  editor = new Editor({
    element: document.querySelector('#editor'),
    content: html,
    editable: enabled,
    extensions: [
      StarterKit.configure({
        heading: { levels: [1, 2, 3] },
        // Formatting the last block should not append a newline to the user's text.
        ...(input ? { trailingNode: false } : {}),
        link: { openOnClick: false, autolink: false, linkOnPaste: false },
      }),
      Highlight,
      TextAlign.configure({ types: ['paragraph', 'heading'] }),
      ParagraphIndent,
      Placeholder.configure({
        placeholder: () => placeholder,
        showOnlyCurrent: false,
      }),
    ],
    editorProps: {
      attributes: () => ({
        role: 'textbox',
        'aria-label': placeholder,
        'aria-multiline': 'true',
        ...(input ? { 'aria-disabled': String(document.body.hasAttribute('data-disabled')) } : {}),
      }),
      handleDOMEvents: {
        compositionstart: refreshCaret,
        compositionend: refreshCaret,
      },
    },
    onCreate: ({ editor }) => updateAppearance(editor),
    onUpdate: ({ editor }) => publishState(editor),
    onFocus: ({ editor }) => updateAppearance(editor),
    onBlur: ({ editor }) => updateAppearance(editor),
    onTransaction: ({ editor }) => { if (input) refreshToolbar(editor) },
    onSelectionUpdate: () => { if (!input) refreshCaret() },
  })
}

window.addEventListener('scroll', refreshCaret, { passive: true })
window.addEventListener('resize', refreshCaret)
document.fonts.ready.then(refreshCaret)

window.sdgRichText = {
  action(action, value) {
    if (!input || !editor?.isEditable) return
    if (action === 'SaveLink') {
      const { from, to } = editor.state.selection
      linkSelection = { from, to }
    } else if (action === 'FinishLink') {
      if (linkSelection) finishLink(editor, linkSelection, value)
      linkSelection = undefined
    } else if (action === 'Blur') {
      linkSelection = undefined
      editor.commands.blur()
    } else {
      runAction(editor, action)
    }
  },
  update(raw) {
    try {
      const update = JSON.parse(raw)
      if (update.input !== undefined) {
        input = update.input
        document.body.toggleAttribute('data-input', input)
      }
      if (update.placeholder !== undefined) placeholder = update.placeholder
      if (update.showPlaceholder !== undefined) {
        document.body.toggleAttribute('data-show-placeholder', update.showPlaceholder)
      }
      if (update.enabled !== undefined) {
        document.body.toggleAttribute('data-disabled', !update.enabled)
      }
      if (!editor) {
        createEditor(update.html, update.enabled)
        return
      }

      if (update.enabled !== undefined && update.enabled !== editor.isEditable) {
        if (!update.enabled) editor.commands.blur()
        editor.setEditable(update.enabled, false)
      }
      if (update.html !== undefined && update.html !== editor.getHTML()) {
        editor.chain().setContent(update.html, { emitUpdate: false }).setMeta('addToHistory', false).run()
      } else if (update.placeholder !== undefined) {
        editor.view.dispatch(editor.state.tr)
      }
      updateAppearance(editor)
    } catch {
      post('error')
    }
  },
}

post('ready')
